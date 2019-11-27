package com.fenghuang.caipiaobao.widget.gift

import com.fenghuang.caipiaobao.widget.gift.bean.GiftIdentify


/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/12- 15:49
 * @ Describe
 *
 */

abstract class BaseGiftBean : GiftIdentify, Cloneable {

    /**
     * 礼物计数
     */
    override var theGiftCount: Int = 0
    /**
     * 礼物刷新时间
     */
    override var theLatestRefreshTime: Long = 0
    /**
     * 当前index
     */
    override var theCurrentIndex: Int = 0

    override fun compareTo(o: GiftIdentify): Int {
        return (this.theLatestRefreshTime - o.theLatestRefreshTime).toInt()
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }
}