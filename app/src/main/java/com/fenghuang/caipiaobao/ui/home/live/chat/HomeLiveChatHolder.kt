package com.fenghuang.caipiaobao.ui.home.live.chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBeanNew
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.ForbiddenWordsDialog
import com.fenghuang.caipiaobao.widget.textview.DraweeSpan
import com.fenghuang.caipiaobao.widget.textview.DraweeTextView


/**
 *  author : QinTian
 *  date   : 2019/8/29 17:38
 */
class HomeLiveChatHolder : MultiTypeViewHolder<HomeLiveChatBeanNew, HomeLiveChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)
    @SuppressLint("SetTextI18n")
    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveChatBeanNew?>(context, parent, R.layout.holder_live_chat) {
        override fun onBindData(data: HomeLiveChatBeanNew?) {
            val tvChatContent = findView<DraweeTextView>(R.id.tvChatContent)
            val builder = SpannableStringBuilder()
            LogUtils.e("---***----" + (data))
            when (data?.type) {

                "first" -> {
                    val start = builder.length
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_system))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).setMargin(0, 0, 0).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                    builder.append("  " + "欢迎来到" + data.gift_name + "直播间，喜欢就点关注吧。")
                    builder.setSpan(ForegroundColorSpan(getColor(R.color.colorRedPrimary)), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    tvChatContent.text = builder
                }
                "subscribe" -> {

                    var start = builder.length
//                        builder.append("[img]")
//                        builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
//                                .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
//                                start, builder.length, ImageSpan.ALIGN_BASELINE)
                    if (data.userType == "2" || data.userType == "1") {
                        getUserType(data.userType, builder, start)
                    } else {
                        getVip(data.vip, builder, start)
                    }
                    builder.append("  " + data.userName)
                    builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    start = builder.length
                    builder.append(" 进入直播间")
                    builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    tvChatContent.text = builder
                }
                "publish" -> {
                    LogUtils.e("-------" + data)
                    var start = builder.length
//                    builder.append("[img]")
//                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
//                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).setMargin(0, 0, 0).build(),
//                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                    if (data.userType == "2" || data.userType == "1") {
                        getUserType(data.userType, builder, start)
                    } else {
                        getVip(data.vip, builder, start)
                    }
                    builder.append("  " + data.userName + "： ")
                    builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    start = builder.length
                    builder.append(data.text + " ")
                    builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    tvChatContent.text = builder
                }
                "gift" -> {
                    when (data.gift_type) {
                        "4" -> {
//                            builder.append("[img]")
//                            builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
//                                    .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
//                                    start, builder.length, ImageSpan.ALIGN_BASELINE)
                            val start = builder.length
                            builder.append(data.userName + " 发送了价值 " + data.gift_price + " 元的红包,数量" + data.gift_num)
                            builder.setSpan(ForegroundColorSpan(getColor(R.color.FFD555)), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            tvChatContent.text = builder
                        }
                        "1" -> {
                            var start = builder.length
//                            builder.append("[img]")
//                            builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
//                                    .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).setMargin(0, -8, 0).build(),
//                                    start, builder.length, ImageSpan.ALIGN_BASELINE)
                            if (data.userType == "2" || data.userType == "1") {
                                getUserType(data.userType, builder, start)
                            } else {
                                getVip(data.vip, builder, start)
                            }
                            builder.append("  " + data.userName + " ")
                            builder.setSpan(ForegroundColorSpan(getColor(R.color.color_999999)), start, builder.length, ImageSpan.ALIGN_BASELINE)
                            start = builder.length
                            builder.append(" 送给主播1个 ")
                            builder.setSpan(ForegroundColorSpan(Color.BLACK), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            start = builder.length
                            builder.append(" " + data.gift_name + "  ")
                            builder.setSpan(ForegroundColorSpan(Color.RED), start, builder.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            start = builder.length
                            builder.append("[img]")
                            builder.setSpan(DraweeSpan.Builder(data.icon)
                                    .setLayout(ViewUtils.dp2px(16), ViewUtils.dp2px(14)).build(),
                                    start, builder.length, ImageSpan.ALIGN_BASELINE)
                            tvChatContent.setLineSpacing(15F, 3F)
                            tvChatContent.text = builder
                        }
                    }
                }
            }


            tvChatContent.setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    if (UserInfoSp.getUserType() == "2") {
                        val dialog = ForbiddenWordsDialog(getContext()!!, data!!)
                        dialog.show()
                    }
                }
            }
        }

        private fun idToUri(resourceId: Int): String {
            return "android.resource://" + context.packageName + "/" + resourceId
        }

        //Vip等级
        private fun getVip(vip: String, builder: SpannableStringBuilder, start: Int) {
            when (vip) {
                "1" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_vip_1))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "2" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v2))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "3" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v3))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "4" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v4))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "5" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v5))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "6" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v6))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "7" -> {
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.v7))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
            }
        }

        //用户或者主播
        private fun getUserType(userType: String, builder: SpannableStringBuilder, start: Int) {

            when (userType) {
                "1" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_anchor))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
                "2" -> {
                    builder.append("[img]")
                    builder.setSpan(DraweeSpan.Builder(idToUri(R.mipmap.ic_live_chat_manager))
                            .setLayout(ViewUtils.dp2px(40), ViewUtils.dp2px(14)).build(),
                            start, builder.length, ImageSpan.ALIGN_BASELINE)
                }
            }
        }
    }
}