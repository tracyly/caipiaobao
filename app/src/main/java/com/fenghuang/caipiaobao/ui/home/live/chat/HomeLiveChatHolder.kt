package com.fenghuang.caipiaobao.ui.home.live.chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveSmallAnimatorBean
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.textview.DraweeSpan
import com.fenghuang.caipiaobao.widget.textview.DraweeTextView
import com.hwangjr.rxbus.RxBus


/**
 *  author : QinTian
 *  date   : 2019/8/29 17:38
 */
class HomeLiveChatHolder : MultiTypeViewHolder<HomeLiveChatBean, HomeLiveChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)
    @SuppressLint("SetTextI18n")
    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveChatBean?>(context, parent, R.layout.holder_live_chat) {
        override fun onBindData(data: HomeLiveChatBean?) {
            val tvChatContent = findView<DraweeTextView>(R.id.tvChatContent)
            val builder = SpannableStringBuilder()
            when (data?.type) {
                "first" -> {
                    val start = builder.length
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_system))
                            .setLayout(ViewUtils.dp2px(30), ViewUtils.dp2px(12)).build(),
                            start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.append("  " + "欢迎来到" + data.gift_name + "直播间，喜欢就点关注吧。")
                    builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvChatContent.text = builder
                }
                "subscribe" -> {
                    var start = builder.length
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
                            .setLayout(ViewUtils.dp2px(30), ViewUtils.dp2px(12)).build(),
                            start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.append("  " + data.userName)
                    builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    start = builder.length
                    builder.append(" 进入直播间")
                    builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvChatContent.text = builder
                }
                "publish" -> {
                    if (isNotEmpty(data.text)) {
                        var start = builder.length
                        builder.append("[img]")
                        builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
                                .setLayout(ViewUtils.dp2px(30), ViewUtils.dp2px(12)).build(),
                                start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        builder.append("  " + data.userName + ": ")
                        builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        start = builder.length
                        builder.append(data.text + " ")
                        builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        tvChatContent.text = builder
                    }
                }
                "gift" -> {
                    when (data.gift_type) {
                        4 -> {
                            var start = builder.length
                            builder.append("[img]")
                            builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
                                    .setLayout(ViewUtils.dp2px(30), ViewUtils.dp2px(12)).build(),
                                    start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            builder.append("  " + data.userName + ":")
                            builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            start = builder.length
                            builder.append(" 发送了价值" + data.gift_price + "的红包,数量" + data.gift_num)
                            builder.setSpan(ForegroundColorSpan(Color.RED), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            tvChatContent.text = builder
                        }
                        1 -> {
                            var start = builder.length
                            builder.append("[img]")
                            builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
                                    .setLayout(ViewUtils.dp2px(30), ViewUtils.dp2px(12)).build(),
                                    start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            builder.append("  " + data.userName + ":")
                            builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            start = builder.length
                            builder.append(" 送给主播1个 ")
                            builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            start = builder.length
                            builder.append(" " + data.gift_name + "  ")
                            builder.setSpan(ForegroundColorSpan(Color.RED), start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            start = builder.length
                            builder.append("[img]")
                            builder.setSpan(DraweeSpan.Builder(data.icon)
                                    .setLayout(ViewUtils.dp2px(20), ViewUtils.dp2px(20)).setMargin(2).build(),

                                    start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            tvChatContent.text = builder
                            if (!data.isMe) {
                                RxBus.get().post(HomeLiveSmallAnimatorBean(data.gift_id, data.gift_name, data.icon, data.user_id, data.avatar.toString(), UserInfoSp.getUserNickName().toString()))
                            }
                        }
                    }
                }
            }
        }

        private fun idToUri(resourceId: Int): String {
            return "android.resource://" + context.packageName + "/" + resourceId
        }


    }
}