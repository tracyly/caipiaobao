package com.fenghuang.caipiaobao.ui.main

import android.Manifest
import android.os.Bundle
import com.fenghuang.baselib.base.activity.BasePageActivity
import com.fenghuang.baselib.utils.AppUtils
import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.caipiaobao.function.doOnIOThread
import com.fenghuang.caipiaobao.helper.DestroyHelper
import com.fenghuang.caipiaobao.helper.RxPermissionHelper

/**
 * 主页面，用来填充Fragment,和处理相关的Intent
 */
class MainActivity : BasePageActivity() {

    override fun getPageFragment() = MainFragment()

    override fun onBackPressedSupport() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressedSupport()
        } else {
            if (DebugUtils.isDevModel()) {
                super.onBackPressedSupport()
            } else {
                AppUtils.moveTaskToBack(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initPreData()
        super.onCreate(savedInstanceState)
        checkDialog()
    }

    /***
     * 回到主页面弹出一些列的窗口
     */
    private fun checkDialog() {

        // 权限弹窗
        RxPermissionHelper.request(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    /**
     * 退出时取消网络相关的请求
     */
    override fun onDestroy() {
        DestroyHelper.onDestroy()
        super.onDestroy()
    }

    /**
     * 初始化一些数据
     */
    private fun initPreData() {
        doOnIOThread {
        }
    }
}
