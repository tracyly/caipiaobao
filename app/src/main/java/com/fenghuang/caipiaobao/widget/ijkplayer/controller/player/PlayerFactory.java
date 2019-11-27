package com.fenghuang.caipiaobao.widget.ijkplayer.controller.player;

public abstract class PlayerFactory<P extends AbstractPlayer> {
    public abstract P createPlayer();
}
