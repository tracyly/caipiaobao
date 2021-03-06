package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.anchor.HomeAnchorFragment
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.ui.mine.data.MineAttentionResponse
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.gif.GifImageView


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录
 *
 */

class MineMyAttentionAdapter(context: Context, val presenter: MineMyAttentionPresenter) : BaseRecyclerAdapter<MineAttentionResponse>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineAttentionResponse> {
        return MineMyAttentionHolder(parent)
    }

    inner class MineMyAttentionHolder(val parent: ViewGroup) : BaseViewHolder<MineAttentionResponse>(getContext(), parent, R.layout.holder_mine_attention) {
        override fun onBindData(data: MineAttentionResponse) {
            ImageManager.loadRoundLogo(data.avatar, findView(R.id.imgAttPhoto))
            setText(R.id.tvAttName, data.nickname)
            setText(R.id.tvAttDes, data.sign)
            LogUtils.e("-----****-" + data.live_status + "-----*****---" + data.nickname)
            if (data.live_status == 1) {
                setVisibility(R.id.imgIsLive, true)
                findView<GifImageView>(R.id.imgIsLive).setGifResource(R.drawable.ic_home_live_gif)
                findView<GifImageView>(R.id.imgIsLive).play(-1)
            } else setGone(R.id.imgIsLive)
            findView<RelativeLayout>(R.id.btnDelete).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    remove(getDataPosition())
                    presenter.unDepose(UserInfoSp.getUserId(), data.anchor_id)
                }
            }
            findView<LinearLayout>(R.id.linGoToLive).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    LaunchUtils.startFragment(getContext(), HomeLiveDetailsFragment.newInstance(data.anchor_id, data.nickname, data.live_status, data.avatar, "MineAttention"))
                }


            }
            findView<RelativeLayout>(R.id.rlPhoto).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    LaunchUtils.startFragment(getContext(), HomeAnchorFragment.newInstance(data.anchor_id, false))
                }
            }
            findView<LinearLayout>(R.id.linCenter).setOnClickListener {
                if (FastClickUtils.isFastClick()) {
                    LaunchUtils.startFragment(getContext(), HomeAnchorFragment.newInstance(data.anchor_id, false))
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}