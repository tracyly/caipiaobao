package com.fenghuang.caipiaobao.app

import android.os.StrictMode
import android.util.Log
import com.facebook.common.logging.FLog
import com.facebook.common.logging.FLogDefaultLoggingDelegate
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.fenghuang.baselib.app.BaseApplication
import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.caipiaobao.BuildConfig
import com.fenghuang.caipiaobao.constant.AppConfigConstant
import com.fenghuang.caipiaobao.function.doOnIOThread
import com.fenghuang.caipiaobao.manager.PushManager
import com.fenghuang.caipiaobao.manager.StatisticManager
import com.fenghuang.caipiaobao.ui.service.InitDataService
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.ijk.IjkPlayerFactory
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoViewConfig
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoViewManager
import com.github.moduth.blockcanary.BlockCanary
import com.pingerx.imagego.core.ImageGo
import com.pingerx.imagego.glide.GlideImageStrategy
import com.pingerx.rxnetgo.RxNetGo
import com.squareup.leakcanary.LeakCanary


/**
 * 项目启动初始化
 */

class CaiPiaoBaoApplication : BaseApplication() {

    companion object {
        private lateinit var mInstance: CaiPiaoBaoApplication
        fun getInstance(): BaseApplication {
            return mInstance
        }
    }

    override fun getCurrentEnvModel() = AppConfigConstant.ENV_DEVELOP

    override fun isEnvSwitch() = AppConfigConstant.ENV_SWITCH

    override fun isEnvLog() = AppConfigConstant.ENV_LOG

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initVideoViewManager()
    }

    override fun initMainProcess() {
        initNetWork()
        initImageLoader()
        initJPush()
        initStatistic()
        startInitDataService()
        // 测试工具初始化
        initTestTools()
        initFresco()
    }

    override fun initThreadProcess() {
        doOnIOThread { }
    }


    private fun initNetWork() {
        RxNetGo.getInstance().init(this).debug(DebugUtils.isDebugModel())
    }

    private fun initImageLoader() {
        ImageGo.setStrategy(GlideImageStrategy()).setDebug(DebugUtils.isDebugModel())
    }

    private fun initJPush() {
        PushManager.init(getContext())
    }

    private fun initStatistic() {
        StatisticManager.init(this)
    }

    private fun startInitDataService() {
        // 启动服务去后台加载一些数据
        InitDataService.enqueueWork(getContext(), InitDataService.JOB_INIT)
    }

    private fun initFresco() {
        // initialize fresco with enabled webp
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this)
                .experiment()
                .setWebpSupportEnabled(true)
                .build())
        // for debug
        if (BuildConfig.DEBUG) {
            FLogDefaultLoggingDelegate.getInstance().setApplicationTag("Drawee-text")
            FLog.setMinimumLoggingLevel(Log.VERBOSE)
        }
    }


    private fun initTestTools() {
        //enabledStrictMode()

        if (DebugUtils.isDevModel()) {
            LeakCanary.install(this)
            BlockCanary.install(this, AppBlockCanaryContext()).start()
        }
    }


    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build())
    }

    private fun initVideoViewManager() {
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)
                .setPlayerFactory(IjkPlayerFactory.create(this))
                .build())
    }

}