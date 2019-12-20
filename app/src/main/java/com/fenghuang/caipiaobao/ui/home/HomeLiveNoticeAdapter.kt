package com.fenghuang.caipiaobao.ui.home

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.HomeLivePopResponse
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.gif.GifImageView
import com.hwangjr.rxbus.RxBus

/**
 *  author : Peter
 *  date   : 2019/10/1 13:27
 *  desc   : 直播预告
 */
class HomeLiveNoticeAdapter(context: Context) : BaseRecyclerAdapter<HomeLivePopResponse>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLivePopResponse> {
        return HomeLiveNoticeHolder(parent)
    }

    inner class HomeLiveNoticeHolder(parent: ViewGroup) : BaseViewHolder<HomeLivePopResponse>(getContext(), parent, R.layout.holder_home_live_notice) {
        override fun onBindData(data: HomeLivePopResponse) {

            if (data.avatar != "null") {
                ImageManager.loadRoundFrameUserLogo(data.avatar, findView(R.id.ivLiveNoticeLogo), borderWidth = 5, color = getColor(R.color.color_f7700a))
            } else ImageManager.loadRoundFrameUserLogo(R.mipmap.ic_mine_base_user, findView(R.id.ivLiveNoticeLogo), borderWidth = 5, color = getColor(R.color.color_f7700a))

            setText(R.id.tvLiveNoticeName, data.nickname)
            setText(R.id.tvLiveNoticeGameName, data.name)


            if (data.livestatus == 1) {
                setGone(findView<ImageView>(R.id.imgVideo))
                setVisibility(findView<GifImageView>(R.id.ivLiveStatus), true)
                findView<GifImageView>(R.id.ivLiveStatus).setGifResource(R.drawable.ic_home_live_gif)
                findView<GifImageView>(R.id.ivLiveStatus).play(-1)
                findView<TextView>(R.id.tvLiveNoticeDate).text = "直播中"
            } else findView<TextView>(R.id.tvLiveNoticeDate).text = "未开播"
            //是否关注
            if (data.isFollow) {
                setGone(findView<RoundTextView>(R.id.tvLiveNoticeAttention))
                setVisible(findView<RoundTextView>(R.id.tvLiveNoticeHasAttention))
            } else {
                setVisible(findView<RoundTextView>(R.id.tvLiveNoticeAttention))
                setGone(findView<RoundTextView>(R.id.tvLiveNoticeHasAttention))
            }
            //跳转直播主页
            findView<ImageView>(R.id.ivLiveNoticeLogo).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    startFragment(HomeLiveDetailsFragment.newInstance(getData()?.aid!!, "", getData()?.livestatus!!, getData()?.avatar!!))
                }
            }

            //关注
            findView<RoundTextView>(R.id.tvLiveNoticeAttention).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    if (UserInfoSp.getIsLogin()) {
                        data.isFollow = true
                        setGone(findView<RoundTextView>(R.id.tvLiveNoticeAttention))
                        setVisibility(findView<RoundTextView>(R.id.tvLiveNoticeHasAttention), true)
                        HomeApi.setAttention(UserInfoSp.getUserId(), data.aid) {
                            onSuccess {
                                RxBus.get().post(MineIsAnchorLive(""))
                            }
                            onFailed {
                                ExceptionDialog.showExpireDialog(getContext()!!, it)
                            }
                        }
                    } else ExceptionDialog.showExpireDialog(getContext()!!)
                }
            }
            //取消关注
            findView<RoundTextView>(R.id.tvLiveNoticeHasAttention).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    if (UserInfoSp.getIsLogin()) {
                        setGone(findView<RoundTextView>(R.id.tvLiveNoticeHasAttention))
                        setVisibility(findView<RoundTextView>(R.id.tvLiveNoticeAttention), true)
                        data.isFollow = false
                        HomeApi.setAttention(UserInfoSp.getUserId(), data.aid) {
                            onSuccess {
                                RxBus.get().post(MineIsAnchorLive(""))
                            }
                            onFailed {
                                ExceptionDialog.showExpireDialog(getContext()!!, it)
                            }
                        }
                    } else ExceptionDialog.showExpireDialog(getContext()!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}