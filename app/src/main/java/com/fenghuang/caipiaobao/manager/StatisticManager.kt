package com.fenghuang.caipiaobao.manager

import android.app.Application
import android.content.Context
import com.fenghuang.baselib.utils.AppUtils
import com.fenghuang.baselib.utils.DebugUtils
import com.tencent.mta.track.StatisticsDataAPI
import com.tencent.stat.StatConfig
import com.tencent.stat.StatCrashReporter
import com.tencent.stat.StatMultiAccount
import com.tencent.stat.StatService
import java.util.*
import kotlin.collections.HashMap

/**
 * 数据统计管理器
 */
object StatisticManager {

    private fun getContext(): Context {
        return AppUtils.getContext()
    }

    fun init(application: Application) {
        // 配置属性
        StatConfig.setTLinkStatus(true)
        StatConfig.setDebugEnable(DebugUtils.isDebugModel())
        StatConfig.setInstallChannel(application.applicationContext, AppUtils.getChannel())

        // 注册activity生命周期，统计时长
        StatService.registerActivityLifecycleCallbacks(application)

        // 可视化统计
        StatisticsDataAPI.instance(application)

        // 腾讯统计不需要统计Java异常
        val crashReporter = StatCrashReporter.getStatCrashReporter(application.applicationContext)

        // 关闭异常时的实时上报
        crashReporter.isEnableInstantReporting = false
        // 关闭java异常捕获
        crashReporter.javaCrashHandlerStatus = false
        // 关闭Native c/c++，即so的异常捕获
        // 请根据需要添加，记得so文件
        crashReporter.jniNativeCrashStatus = false
    }

    /**
     * Key-Value事件次数统计
     */
    fun trackKVEvent(event: String, params: Map<String, Any> = HashMap()) {
        val prop = Properties()
        for (key in params.keys) {
            prop.setProperty(key, params[key].toString())
        }
        StatService.trackCustomKVEvent(getContext(), event, prop)
    }

    /**
     * 事件使用时长统计开始
     */
    fun trackBeginKVEvent(event: String, params: Map<String, Any> = HashMap()) {
        val prop = Properties()
        for (key in params.keys) {
            prop.setProperty(key, params[key].toString())
        }
        StatService.trackCustomBeginKVEvent(getContext(), event, prop)
    }

    /**
     * 事件使用时长统计结束
     */
    fun trackCustomEndKVEvent(event: String, params: Map<String, Any> = HashMap()) {
        val prop = Properties()
        for (key in params.keys) {
            prop.setProperty(key, params[key].toString())
        }
        StatService.trackCustomEndKVEvent(getContext(), event, prop)
    }

    /**
     * 上报单个账号，通常用于登陆/刷票登陆/第三方登陆等时机调用
     */
    fun reportMultiAccount(account: StatMultiAccount) {
        StatService.reportMultiAccount(getContext(), account)
    }


}