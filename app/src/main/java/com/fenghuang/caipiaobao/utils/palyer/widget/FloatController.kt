package com.fenghuang.caipiaobao.utils.palyer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.palyer.PIPManager
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.GestureVideoController
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.VideoView

/**
 * 悬浮播放控制器
 * Created by Devlin_n on 2017/6/1.
 */

class FloatController : GestureVideoController, View.OnClickListener {


    private var proLoading: ProgressBar? = null
    private var playButton: ImageView? = null


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.layout_float_controller
    }

    override fun initView() {
        super.initView()
        this.setOnClickListener(this)
        mControllerView.findViewById<View>(R.id.btn_close).setOnClickListener(this)
        mControllerView.findViewById<View>(R.id.btn_skip).setOnClickListener(this)
        mControllerView.findViewById<View>(R.id.float_controller).setOnClickListener(this)
        proLoading = mControllerView.findViewById(R.id.loading)
        playButton = mControllerView.findViewById(R.id.start_play)
        playButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_close) {
            PIPManager.getInstance().stopFloatWindow()
            PIPManager.getInstance().reset()
        } else if (id == R.id.start_play) {
            doPauseResume()
        } else if (id == R.id.float_controller) {
            if (PIPManager.getInstance().mFragment != null) {
//                val intent = Intent(context, PIPManager.getInstance().actClass)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context.startActivity(intent)
                LaunchUtils.startFragment(PIPManager.getInstance().mContext, PIPManager.getInstance().mFragment!!)
            }
        }
    }

    override fun setPlayState(playState: Int) {
        super.setPlayState(playState)
        when (playState) {
            VideoView.STATE_IDLE -> {
                playButton!!.isSelected = false
                playButton!!.visibility = View.VISIBLE
                proLoading!!.visibility = View.GONE
            }
            VideoView.STATE_PLAYING -> {
                playButton!!.isSelected = true
                playButton!!.visibility = View.GONE
                proLoading!!.visibility = View.GONE
                hide()
            }
            VideoView.STATE_PAUSED -> {
                playButton!!.isSelected = false
                playButton!!.visibility = View.VISIBLE
                proLoading!!.visibility = View.GONE
                show(0)
            }
            VideoView.STATE_PREPARING -> {
                playButton!!.visibility = View.GONE
                proLoading!!.visibility = View.VISIBLE
            }
            VideoView.STATE_PREPARED -> {
                playButton!!.visibility = View.GONE
                proLoading!!.visibility = View.GONE
            }
            VideoView.STATE_ERROR -> {
                proLoading!!.visibility = View.GONE
                playButton!!.visibility = View.GONE
            }
            VideoView.STATE_BUFFERING -> {
                playButton!!.visibility = View.GONE
                proLoading!!.visibility = View.VISIBLE
            }
            VideoView.STATE_BUFFERED -> {
                playButton!!.visibility = View.GONE
                proLoading!!.visibility = View.GONE
                if (mMediaPlayer != null) {
                    playButton!!.isSelected = mMediaPlayer.isPlaying
                }
            }
            VideoView.STATE_PLAYBACK_COMPLETED -> {
                show(0)
                removeCallbacks(mShowProgress)
            }
        }
    }


    override fun show() {
        show(mDefaultTimeout)
    }

    private fun show(timeout: Int) {
        if (mCurrentPlayState == VideoView.STATE_BUFFERING) return
        if (!mShowing) {
            playButton!!.visibility = View.VISIBLE
        }
        mShowing = true

        removeCallbacks(mFadeOut)
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout.toLong())
        }
    }


    override fun hide() {
        if (mCurrentPlayState == VideoView.STATE_BUFFERING) return
        if (mShowing) {
            playButton!!.visibility = View.GONE
            mShowing = false
        }
    }
}
