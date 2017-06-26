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