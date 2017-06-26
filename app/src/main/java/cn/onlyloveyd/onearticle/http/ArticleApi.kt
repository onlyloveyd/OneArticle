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

import cn.onlyloveyd.onearticle.bean.Article
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 文 件 名: ArticleApi
 * 创 建 人: 易冬
 * 创建日期: 2017/6/19 15:53
 * 描   述：
 */
interface ArticleApi {
    //https://interface.meiriyiwen.com/article/today?dev=1
    //https://interface.meiriyiwen.com/article/day?dev=1&date=20170216
    //https://interface.meiriyiwen.com/article/random?dev=1
    @GET("today")
    fun getTodayArticle(@Query("dev") dev: Int): Observable<Article>

    @GET("day")
    fun getArticelByDate(@Query("dev") dev: Int, @Query("date") date: String): Observable<Article>


    @GET("random")
    fun getRandomArticle(@Query("dev") dev: Int): Observable<Article>
}