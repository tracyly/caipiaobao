package com.fenghuang.caipiaobao.widget.ijkplayer.controller.player;


public class AndroidMediaPlayerFactory extends PlayerFactory {

    public static AndroidMediaPlayerFactory create() {
        return new AndroidMediaPlayerFactory();
    }

    @Override
    public AndroidMediaPlayer createPlayer() {
        return new AndroidMediaPlayer();
    }
}
