package com.fenghuang.caipiaobao.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.fenghuang.baselib.utils.AppUtils
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.main.MainActivity
import me.jessyan.autosize.internal.CancelAdapt

/**
 *
 * 开屏页面，启动时会有图片背景，不需要使用适配
 */
class SplashActivity : Activity(), CancelAdapt {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setStatusBarByFlags(this)
        setContentView(R.layout.activity_splash)

        AppUtils.postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 100)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_in)
    }
}