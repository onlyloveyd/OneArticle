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
package cn.onlyloveyd.onearticle.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * 文 件 名: Utils
 * 创 建 人: 易冬
 * 创建日期: 2017/6/19 15:48
 * 描   述：
 */
object Utils {

    /**
     * 判断网络可不可用
     * @return true为可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm?.activeNetworkInfo
        if (info != null) {
            return info.isAvailable && info.isConnected
        }
        return false
    }
}