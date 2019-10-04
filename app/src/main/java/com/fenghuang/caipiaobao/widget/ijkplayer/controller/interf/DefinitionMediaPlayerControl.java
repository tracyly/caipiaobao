package com.fenghuang.caipiaobao.widget.ijkplayer.controller.interf;


import com.fenghuang.caipiaobao.widget.ijkplayer.controller.MediaPlayerControl;

import java.util.LinkedHashMap;

/**
 * 清晰度
 * Created by xinyu on 2017/12/25.
 */

public interface DefinitionMediaPlayerControl extends MediaPlayerControl {
    LinkedHashMap<String, String> getDefinitionData();

    void switchDefinition(String definition);
}
