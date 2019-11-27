package com.fenghuang.caipiaobao.data.api

import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_DEV
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_DEV_OTHER
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_OPEN
import com.fenghuang.caipiaobao.data.api.ApiConstant.API_URL_QUIZ
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

    /**
     * 登录其他的BaseUrl
     */
    fun getApiOther(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService(API_URL_DEV_OTHER)
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
     * 竞彩接口
     */
    fun getAipJcUrl(): RxNetGo {
        return RxNetGo.getInstance().getRetrofitService("http://154.206.43.213:18308")
    }


    /**
     * 获取URL
     */
    fun getQuizBaseUrl(): String {
        return if (DebugUtils.isDebugModel()) {
            API_URL_QUIZ
        } else {
            API_URL
        }
    }

    /**
     * 获取开奖URL
     */
    fun getOpenPriseBaseUrl(): String {
        return if (DebugUtils.isDebugModel()) {
            API_URL_OPEN
        } else {
            API_URL
        }
    }

}