package com.fenghuang.caipiaobao.utils.palyer.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.FrameLayout

import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.util.PlayerUtils

/**
 * 悬浮窗控件（解决滑动冲突）
 * Created by Devlin_n on 2017/6/8.
 */

@SuppressLint("ViewConstructor")
class FloatView(context: Context, private var mDownX: Int, private var mDownY: Int//手指按下时相对于悬浮窗的坐标
) : FrameLayout(context) {

    private var mWindowManager: WindowManager? = null
    private var mParams: WindowManager.LayoutParams? = null

    private var mDownRawX: Int = 0
    private var mDownRawY: Int = 0//手指按下时相对于屏幕的坐标

    init {
        init()
    }


    private fun init() {
        setBackgroundResource(R.drawable.shape_float_window_background)
        val padding = PlayerUtils.dp2px(context, 1f)
        setPadding(padding, padding, padding, padding)
        initWindow()
    }

    private fun initWindow() {
        mWindowManager = PlayerUtils.getWindowManager(context.applicationContext)
        mParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mParams!!.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        // 设置图片格式，效果为背景透明
        mParams!!.format = PixelFormat.TRANSLUCENT
        mParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mParams!!.windowAnimations = R.style.FloatWindowAnimation
        mParams!!.gravity = Gravity.START or Gravity.TOP // 调整悬浮窗口至右下角
        // 设置悬浮窗口长宽数据
        val width = PlayerUtils.dp2px(context, 250f)
        mParams!!.width = width
        mParams!!.height = width * 9 / 16
        mParams!!.x = mDownX
        mParams!!.y = mDownY
    }

    /**
     * 添加至窗口
     */
    fun addToWindow(): Boolean {
        if (mWindowManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!isAttachedToWindow) {
                    mWindowManager!!.addView(this, mParams)
                    return true
                } else {
                    return false
                }
            } else {
                try {
                    if (parent == null) {
                        mWindowManager!!.addView(this, mParams)
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }

            }
        } else {
            return false
        }
    }

    /**
     * 从窗口移除
     */
    fun removeFromWindow(): Boolean {
        if (mWindowManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return if (isAttachedToWindow) {
                    mWindowManager!!.removeViewImmediate(this)
                    true
                } else {
                    false
                }
            } else {
                return try {
                    if (parent != null) {
                        mWindowManager!!.removeViewImmediate(this)
                    }
                    true
                } catch (e: Exception) {
                    false
                }

            }
        } else {
            return false
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                mDownRawX = ev.rawX.toInt()
                mDownRawY = ev.rawY.toInt()
                mDownX = ev.x.toInt()
                mDownY = (ev.y + PlayerUtils.getStatusBarHeight(context)).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val absDeltaX = Math.abs(ev.rawX - mDownRawX)
                val absDeltaY = Math.abs(ev.rawY - mDownRawY)
                intercepted = absDeltaX > ViewConfiguration.get(context).scaledTouchSlop || absDeltaY > ViewConfiguration.get(context).scaledTouchSlop
            }
        }
        return intercepted
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                mParams!!.x = x - mDownX
                mParams!!.y = y - mDownY
                mWindowManager!!.updateViewLayout(this, mParams)
            }
        }
        return super.onTouchEvent(event)
    }
}
