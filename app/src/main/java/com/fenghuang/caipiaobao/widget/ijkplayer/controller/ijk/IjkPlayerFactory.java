package com.fenghuang.caipiaobao.widget.ijkplayer.controller.ijk;

import android.content.Context;

import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.AbstractPlayer;
import com.fenghuang.caipiaobao.widget.ijkplayer.controller.player.PlayerFactory;


public class IjkPlayerFactory extends PlayerFactory {

    private Context mContext;

    public IjkPlayerFactory(Context context) {
        mContext = context.getApplicationContext();
    }

    public static IjkPlayerFactory create(Context context) {
        return new IjkPlayerFactory(context);
    }

    @Override
    public AbstractPlayer createPlayer() {
        return new IjkPlayer(mContext);
    }
}
