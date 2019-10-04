package com.fenghuang.caipiaobao.widget.ijkplayer.controller.player;

abstract class ProgressManager {

    abstract void saveProgress(String url, long progress);

    abstract long getSavedProgress(String url);

}
