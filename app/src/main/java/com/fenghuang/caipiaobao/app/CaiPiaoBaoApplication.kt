package com.fenghuang.caipiaobao.app

import android.os.StrictMode
import android.util.Log
import com.facebook.common.logging.FLog
import com.facebook.common.logging.FLogDefaultLoggingDelegate
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.fenghuang.baselib.app.BaseApplication
import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.BuildConfig
import com.fenghuang.caipiaobao.constant.AppConfigConstant
import com.fenghuang.caipiaobao.function.doOnIOThread
import com.fenghuang.caipiaobao.manager.PushManager
import com.fenghuang.caipiaobao.manager.StatisticManager
import com.fenghuang.caipiaobao.ui.service.InitDataService
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.IjkPlayerFactory
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewConfig
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoViewManager
import com.github.moduth.blockcanary.BlockCanary
import com.pingerx.imagego.core.ImageGo
import com.pingerx.imagego.glide.GlideImageStrategy
import com.pingerx.rxnetgo.RxNetGo
import com.squareup.leakcanary.LeakCanary
import com.tencent.smtt.sdk.QbSdk


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

        initX5Web()
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
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                //                .setPlayerFactory(IjkPlayerFactory.create())
                .setPlayerFactory(IjkPlayerFactory.create())
                //                .setRenderViewFactory(SurfaceRenderViewFactory.create())
                //                .setEnableOrientation(true)
                //                .setEnableAudioFocus(false)
                //                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                //                .setAdaptCutout(false)
                //                .setPlayOnMobileNetwork(true)
                //                .setProgressManager(new ProgressManagerImpl())
                .build())

//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
    }


    fun initX5Web() {

        //  预加载X5内核
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //初始化完成回调
            }

            override fun onCoreInitFinished() {
            }
        })

        ToastUtils.init(getApplication())

    }




}