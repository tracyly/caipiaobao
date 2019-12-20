package com.fenghuang.caipiaobao.widget.ijkplayer.controller.util;

import android.content.Context;
import android.view.OrientationEventListener;

/**
 * @ Author  QinTian
 * @ Date  2019/11/19- 14:29
 * @ Describe 设备方向监听
 */
public class OrientationHelper extends OrientationEventListener {

    private long mLastTime;

    private OnOrientationChangeListener mOnOrientationChangeListener;

    public OrientationHelper(Context context) {
        super(context);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime < 300) return;//300毫秒检测一次
        if (mOnOrientationChangeListener != null) {
            mOnOrientationChangeListener.onOrientationChanged(orientation);
        }
        mLastTime = currentTime;
    }

    public void setOnOrientationChangeListener(OnOrientationChangeListener onOrientationChangeListener) {
        mOnOrientationChangeListener = onOrientationChangeListener;
    }

    public interface OnOrientationChangeListener {
        void onOrientationChanged(int orientation);
    }
}