package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.controller.GestureVideoController;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player.VideoView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.util.L;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview.component.PipControlView;

/**
 * 悬浮播放控制器
 * Created by dueeeke on 2017/6/1.
 */
public class FloatController extends GestureVideoController {

    public FloatController(@NonNull Context context) {
        super(context);
    }

    public FloatController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
//        addControlComponent(new CompleteView(getContext()));
//        addControlComponent(new ErrorView(getContext()));
        addControlComponent(new PipControlView(getContext()));
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        switch (playState) {
            //调用release方法会回到此状态
            case VideoView.STATE_IDLE:
                L.e("STATE****_______IDLE");

                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE****_______PLAYING");

                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE****_______PAUSED");

                break;
            case VideoView.STATE_PREPARING:
                L.e("STATE****_______PREPARING");

                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE****_______PREPARED");

                break;
            case VideoView.STATE_ERROR:
                mControlWrapper.replay(true);
                L.e("STATE****_______ERROR");

                break;
            case VideoView.STATE_BUFFERING:
                L.e("STATE****_______BUFFERING");

                break;
            case VideoView.STATE_BUFFERED:
                L.e("STATE****_______BUFFERED");

                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:

                break;

        }
    }
}
