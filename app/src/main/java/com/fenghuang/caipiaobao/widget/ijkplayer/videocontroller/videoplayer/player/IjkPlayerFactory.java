package com.fenghuang.caipiaobao.widget.ijkplayer.videocontroller.videoplayer.player;

import android.content.Context;

public class IjkPlayerFactory extends PlayerFactory<IjkPlayer> {


    public static IjkPlayerFactory create() {
        return new IjkPlayerFactory();
    }

    @Override
    public IjkPlayer createPlayer(Context context) {
        return new IjkPlayer(context);
    }
}
