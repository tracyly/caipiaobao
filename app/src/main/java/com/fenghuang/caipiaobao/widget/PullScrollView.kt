package com.fenghuang.caipiaobao.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ScrollView

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/8- 16:56
 * @ Describe ios风格ScrollView
 *
 */

class PullScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private var mFirstPosition = 0f
    private var mScaling: Boolean? = false
    private var dropZoomView: View? = null
    private var dropZoomViewWidth: Int = 0
    private var dropZoomViewHeight: Int = 0
    private var inner: View? = null
    private var yPoint: Float = 0.toFloat()
    private val normal = Rect()
    private var isCount = false
    private var lastX = 0f
    private var lastY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private var distanceX = 0f
    private var distanceY = 0f
    private var upDownSlide = false

    private val isNeedAnimation: Boolean
        get() = !normal.isEmpty

    private val isNeedMove: Boolean
        get() {
            val offset = inner!!.measuredHeight - height
            val scrollY = scrollY
            return scrollY == 0 || scrollY == offset
        }

    private fun init() {
        overScrollMode = View.OVER_SCROLL_NEVER
        if (getChildAt(0) != null) {
            inner = getChildAt(0)
            val vg = getChildAt(0) as ViewGroup
            if (vg.getChildAt(0) != null) {
                dropZoomView = vg.getChildAt(0)
            }
        }
    }

    override fun onFinishInflate() {
        init()
        super.onFinishInflate()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentX = ev.x
        currentY = ev.y
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                distanceX = currentX - lastX
                distanceY = currentY - lastY
                if (Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > 12) {
                    upDownSlide = true
                }
            }
        }
        lastX = currentX
        lastY = currentY
        if (upDownSlide && inner != null) commOnTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    /***
     * 触摸事件
     */
    private fun commOnTouchEvent(ev: MotionEvent) {
        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
            dropZoomViewWidth = dropZoomView!!.measuredWidth
            dropZoomViewHeight = dropZoomView!!.measuredHeight
        }
        when (ev.action) {
            MotionEvent.ACTION_UP -> {
                mScaling = false
                replyImage()

                if (isNeedAnimation) {
                    animation()
                    isCount = false
                }
                clear0()
            }
            MotionEvent.ACTION_MOVE -> {
                val preY = yPoint
                val nowY = ev.y
                var deltaY = (preY - nowY).toInt()
                if (!isCount) {
                    deltaY = 0
                }
                yPoint = nowY
                if (isNeedMove) {
                    if (normal.isEmpty) {
                        normal.set(inner!!.left, inner!!.top,
                                inner!!.right, inner!!.bottom)
                    }
                    // 移动布局
                    inner!!.layout(inner!!.left, inner!!.top - deltaY / 2,
                            inner!!.right, inner!!.bottom - deltaY / 2)
                }
                isCount = true
                if ((!mScaling!!)) {
                    if (scrollY == 0) {
                        mFirstPosition = ev.y
                    } else {
                        return
                    }
                }
                val distance = ((ev.y - mFirstPosition) * 0.5).toInt()
                if (distance < 0) {
                    return
                }
                mScaling = true
                setZoom((1 + distance).toFloat())
            }
        }
    }

    /***
     * 回缩
     */
    private fun animation() {
        val ta = TranslateAnimation(0f, 0f, inner!!.top.toFloat(),
                normal.top.toFloat())
        ta.duration = 200
        inner!!.startAnimation(ta)
        inner!!.layout(normal.left, normal.top, normal.right, normal.bottom)
        normal.setEmpty()
    }

    private fun replyImage() {
        //final float distance = dropZoomView.getMeasuredWidth() - dropZoomViewWidth;
        val distance = (dropZoomView!!.measuredHeight - dropZoomViewHeight).toFloat()
        val anim = ObjectAnimator.ofFloat(0.0f, 1.0f).setDuration((distance * 0.7).toLong())

        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            setZoom(distance - distance * cVal)
        }
        anim.start()
    }

    private fun setZoom(s: Float) {
        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
            return
        }
        val lp = dropZoomView!!.layoutParams
        //lp.width = (int) (dropZoomViewWidth + s/2);
        lp.width = dropZoomViewWidth
        lp.height = (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth)).toInt()
        dropZoomView!!.layoutParams = lp
    }

    private fun clear0() {
        lastX = 0f
        lastY = 0f
        distanceX = 0f
        distanceY = 0f
        upDownSlide = false
    }

}
