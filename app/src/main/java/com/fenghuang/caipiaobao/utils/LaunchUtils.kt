package com.fenghuang.caipiaobao.utils

import android.content.Context
import android.content.Intent
import com.fenghuang.baselib.base.activity.BaseActivity
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.utils.AppUtils
import com.fenghuang.baselib.web.WebActivity
import com.fenghuang.caipiaobao.ui.main.MainActivity

/**
 * 页面跳转的工具类
 */
object LaunchUtils {

    fun startActivity(context: Context?, clazz: Class<*>) {
        context?.startActivity(Intent(context, clazz))
    }

    fun startActivity(context: Context?, intent: Intent) {
        context?.startActivity(intent)
    }

    fun startFragment(context: Context?, fragment: BaseFragment) {
        if (context is BaseActivity) {
            context.start(fragment)
        }
    }


    /**
     * 打开主页
     */
    fun startMain(context: Context?) {
        startActivity(context, MainActivity::class.java)
    }


    /**
     * 打开浏览器
     */
    fun startBrowser(url: String?) {
        AppUtils.startBrowser(url)
    }

    /**
     * 打开应用市场
     */
    fun startAppMarket() {
        AppUtils.startAppMarket()
    }

    /**
     * 跳转到Web页面，跳转Activity
     */
    fun startWebActivity(context: Context?, url: String?, title: String? = null, canBack: Boolean = true) {
        WebActivity.start(context, url, title, canBack)
    }
}