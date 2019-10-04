package com.fenghuang.caipiaobao.data.api

import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_DEV
import com.pingerx.rxnetgo.RxNetGo

/**
 * 网络请求基类
 */
interface BaseApi {


    /**
     * 获取URL
     */
    fun getBaseUrl(): String {
        return if (DebugUtils.isDebugModel()) {
            API_URL_DEV
        } else {
            API_URL
        }
    }

    /**
     * 获取默认的Service
     * 需要子类绑定URL
     */
    fun getApi(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService(getBaseUrl())
    }
}