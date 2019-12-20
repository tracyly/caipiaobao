package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player;

import android.content.Context;

public class AndroidMediaPlayerFactory extends PlayerFactory<AndroidMediaPlayer> {

    public static AndroidMediaPlayerFactory create() {
        return new AndroidMediaPlayerFactory();
    }

    @Override
    public AndroidMediaPlayer createPlayer(Context context) {
        return new AndroidMediaPlayer(context);
    }
}
