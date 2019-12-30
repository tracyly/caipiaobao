package com.fenghuang.caipiaobao.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import me.jessyan.autosize.internal.CancelAdapt


/**
 *
 * 开屏页面，启动时会有图片背景，不需要使用适配
 */
class SplashActivity : Activity(), CancelAdapt {

    var time = 3
    var handler = Handler()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

        StatusBarUtils.setStatusBarByFlags(this)
        setContentView(R.layout.activity_splash)
//
//        AppUtils.postDelayed(Runnable {
//            tvDaoJiShi.text = time.toString() + "秒跳转"
//            time--
//            if (time == 0) {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//            AppUtils.postDelayed(this,1000)
//        }, 3000)

        handler.postDelayed(object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                //文字显示3秒跳转
                tvDaoJiShi.text = time.toString() + "秒跳转"
                time--
                if (time == 0) {
                    startActivity(Intent(baseContext, MainActivity::class.java))
                    finish()
                    return
                }
                handler.postDelayed(this, 1000)
            }
        }, 1000)

        tvDaoJiShi.setOnClickListener {
            startActivity(Intent(baseContext, MainActivity::class.java))
            finish()
        }
    }

}