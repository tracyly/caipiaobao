package com.fenghuang.caipiaobao.widget.lighter.interfaces;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 */
public interface OnLighterListener {
    /**
     * When the highlight is displayed, this method will be called back.
     *
     * @param index index of the number of highlights you configured.
     */
    void onShow(int index);

    /**
     * Call back this method when the all highlights has been displayed.
     */
    void onDismiss();
}
