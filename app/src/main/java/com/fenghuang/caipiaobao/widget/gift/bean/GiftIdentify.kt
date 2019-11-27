package com.fenghuang.caipiaobao.widget.gift.bean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/12- 15:28
 * @ Describe
 *
 */

interface GiftIdentify : Comparable<GiftIdentify> {

    /**
     * 礼物Id
     * @return
     */
    var theGiftId: Int

    /**
     * 用户Id
     * @return
     */
    var theUserId: Int

    /**
     * 礼物累计数
     * @return
     */
    var theGiftCount: Int

    /**
     * 单次礼物赠送数目
     * @return
     */
    var theSendGiftSize: Int

    /**
     * 礼物停留时间
     * @return
     */
    var theGiftStay: Long

    /**
     * 礼物最新一次刷新时间戳
     * @return
     */
    var theLatestRefreshTime: Long

    /**
     * 礼物索引
     * @return
     */
    var theCurrentIndex: Int
}
