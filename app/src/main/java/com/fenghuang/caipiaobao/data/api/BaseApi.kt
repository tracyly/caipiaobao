package com.fenghuang.caipiaobao.data.api

import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_DEV
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_DEV_OTHER_TEST
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_OPEN
import com.pingerx.rxnetgo.RxNetGo

/**
 * 网络请求基类
 */
interface BaseApi {

    /**
     * 获取URL  admin
     */
    fun getBaseUrl(): String {
        return API_URL_DEV
    }

    /**
     * 获取URL  Me
     */
    fun getBaseUrlMe(): String {
        return API_URL_DEV_OTHER_TEST
    }


    /**
     * 获取默认的Service
     * 需要子类绑定URL
     */
    fun getApi(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService(getBaseUrl())
    }

    /**
     * 登录其他的BaseUrl
     */
    fun getApiOther(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService(getBaseUrlMe())
    }


    fun getAipQuizUrl(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService("http://47.75.130.69:18308")
    }


    /**
     * 开奖
     */
    fun getAipOpenUrl(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService(getOpenPriseBaseUrl())
    }

    /**
     * 获取开奖URL
     */
    fun getOpenPriseBaseUrl(): String {
        return API_URL_OPEN

    }

}