package com.fenghuang.caipiaobao.data.api

import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.utils.JsonUtils
import java.lang.reflect.Type

/**
 * Web页面的接口管理
 */
object WebUrlProvider {

    const val API_URL_WEB_SOCKET = "ws://47.244.212.147:2347"

    private fun getBaseUrl(): String {
        return if (DebugUtils.isDebugModel()) {
            ApiConstant.WEB_URL
        } else {
            ApiConstant.WEB_URL_DEV
        }
    }

    fun <T> getData(message: String?, type: Type): T? {
        try {
            val content = message ?: ""
            return if (isEmpty(content)) null else {
                JsonUtils.fromJson(content, type)
            }
        } catch (e: Exception) {
            LogUtils.e(e)
        }
        return null
    }

}