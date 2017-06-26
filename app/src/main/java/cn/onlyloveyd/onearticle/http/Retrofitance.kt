/**
 * Copyright 2017 yidong
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.onlyloveyd.onearticle.http

import android.content.Context
import cn.onlyloveyd.onearticle.app.App
import cn.onlyloveyd.onearticle.bean.Article
import cn.onlyloveyd.onearticle.utils.Utils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 文 件 名: Retrofitance
 * 创 建 人: 易冬
 * 创建日期: 2017/6/19 15:48
 * 描   述：
 */
class Retrofitance private constructor() {
    val API_BASE_URL = "https://interface.meiriyiwen.com/article/"
    val TIMEOUT: Long = 20
    private var mRetrofit: Retrofit? = null
    private var mArticleApi: ArticleApi? = null

    init {
        if (mRetrofit == null) createRetrofit(App.instance)
    }

    companion object {
        fun getInstance(): Retrofitance {
            return Single.Instance
        }
    }

    private object Single {
        val Instance = Retrofitance()
    }

    /**
     * 配置OkHttpClient、Retrofit、NetService三个关键对象
     * @param context
     */
    fun createRetrofit(context: Context) {
        mRetrofit = Retrofit.Builder().client(constructClient(context)).baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        mArticleApi = mRetrofit?.create(ArticleApi::class.java)
    }

    /**
     * 构造OkHttpClient
     * @param context
     * *
     * @return
     */
    private fun constructClient(context: Context): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024
        val file = context.externalCacheDir
        val client = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(Cache(file, cacheSize))
                .addNetworkInterceptor(getNetworkInterceptor())
                .addInterceptor(getInterceptor())
                .retryOnConnectionFailure(true)
                .build()

        return client
    }

    /**
     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
     */
    fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!Utils.isNetworkAvailable(App.instance)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            chain.proceed(request)
        }
    }

    fun getNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (Utils.isNetworkAvailable(App.instance)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周
                val maxStale: Long = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build()
            }
            response
        }
    }

    fun getTodayArticle(observer: Observer<Article>, dev: Int): Unit {
        mArticleApi?.let {
            it.getTodayArticle(dev)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }

    fun getArticelByDate(observer: Observer<Article>, dev: Int, date: String) {
        mArticleApi?.let {
            it.getArticelByDate(dev, date)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }


    fun getRandomArticle(observer: Observer<Article>, dev: Int) {
        mArticleApi?.let {
            it.getRandomArticle(dev)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }
    }
}
