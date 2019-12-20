package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.CompleteView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.component.ErrorView;
import com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.controller.GestureVideoController;
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
        addControlComponent(new CompleteView(getContext()));
        addControlComponent(new ErrorView(getContext()));
        addControlComponent(new PipControlView(getContext()));
    }
}
