package com.fenghuang.caipiaobao.ui.widget

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/27- 16:43
 * @ Describe
 *
 */

object X5WebView {

    fun initWebViewSettings(webView: WebView, context: Context) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                //判断Url
                if (url != null && view != null) {
                    judgeUrl(url, view, context)
                }
                return false
            }
        }
        val webSetting = webView.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(false)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    private fun judgeUrl(url: String, view: WebView, context: Context): Boolean {
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
                context.startActivity(Intent("android.intent.action.VIEW", Uri.parse(url)))
            } catch (e: Exception) {

                AlertDialog.Builder(context).setMessage("未检测到支付宝客户端，请安装后重试。")
                        .setPositiveButton("立即安装") { dialog, which ->
                            val alipayUrl = Uri.parse("https://d.alipay.com")
                            context.startActivity(Intent("android.intent.action.VIEW", alipayUrl))
                        }.setNegativeButton("取消", null).show()
            }
            return true
        }
        // ------- 处理结束 -------
        if (!(url.startsWith("http") || url.startsWith("https"))) {
            return true
        }
        view.loadUrl(url)
        return true
    }
}