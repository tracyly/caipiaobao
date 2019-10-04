package com.fenghuang.caipiaobao.utils.palyer

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.caipiaobao.app.CaiPiaoBaoApplication
import com.fenghuang.caipiaobao.utils.palyer.widget.FloatController
import com.fenghuang.caipiaobao.utils.palyer.widget.FloatView
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.widget.DefinitionVideoView

/**
 * 悬浮播放
 * Created by Devlin_n on 2018/3/30.
 */

class PIPManager {
    val mVideoView: DefinitionVideoView = DefinitionVideoView(CaiPiaoBaoApplication.getInstance())
    private val mFloatView: FloatView = FloatView(CaiPiaoBaoApplication.getInstance(), 0, 0)
    private val mFloatController: FloatController = FloatController(CaiPiaoBaoApplication.getInstance())
    var isStartFloatWindow: Boolean = false
        private set
    var playingPosition = -1
    var actClass: Class<*>? = null
    var mFragment: BaseFragment? = null
    var mContext: Context? = null


    fun startFloatWindow() {
        if (isStartFloatWindow) return
        removePlayerFormParent()
        mFloatController.setPlayState(mVideoView.currentPlayState)
        mFloatController.setPlayerState(mVideoView.currentPlayerState)
        mVideoView.setVideoController(mFloatController)
        mFloatView.addView(mVideoView)
        mFloatView.addToWindow()
        isStartFloatWindow = true
    }

    fun stopFloatWindow() {
        if (!isStartFloatWindow) return
        mFloatView.removeFromWindow()
        removePlayerFormParent()
        isStartFloatWindow = false
    }

    private fun removePlayerFormParent() {
        val parent = mVideoView.parent
        if (parent is ViewGroup) {
            parent.removeView(mVideoView)
        }
    }

    fun pause() {
        if (isStartFloatWindow) return
        mVideoView.pause()
    }

    fun resume() {
        if (isStartFloatWindow) return
        mVideoView.resume()
    }

    fun reset() {
        if (isStartFloatWindow) return
        removePlayerFormParent()
        mVideoView.setVideoController(null)
        mVideoView.release()
        playingPosition = -1
        actClass = null
        mFragment = null
    }

    fun onBackPress(): Boolean {
        return !isStartFloatWindow && mVideoView.onBackPressed()
    }

    /**
     * 显示悬浮窗
     */
    fun setFloatViewVisible() {
        if (isStartFloatWindow) {
            mVideoView.resume()
            mFloatView.visibility = View.VISIBLE
        }
    }

    fun setContext(context: Context) {
        mContext = context
    }

    companion object {

        private var instance: PIPManager? = null

        fun getInstance(): PIPManager {
            if (instance == null) {
                synchronized(PIPManager::class.java) {
                    if (instance == null) {
                        instance = PIPManager()
                    }
                }
            }
            return instance!!
        }
    }

}
