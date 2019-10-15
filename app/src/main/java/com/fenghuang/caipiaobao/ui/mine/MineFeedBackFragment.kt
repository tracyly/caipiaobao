package com.fenghuang.caipiaobao.ui.mine

import android.text.TextUtils
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_mine_feedback.*
import kotlinx.android.synthetic.main.fragment_mine_presonal.publish_ed_desc
import kotlinx.android.synthetic.main.fragment_mine_presonal.publish_text_num


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 14:50
 * @ Describe 用户反馈
 *
 */

class MineFeedBackFragment : BaseMvpFragment<MineFeedBackPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineFeedBackPresenter()

    override fun getContentResID() = R.layout.fragment_mine_feedback

    override fun getPageTitle() = getString(R.string.mine_feed_back)

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        mPresenter.initEditFeedBack(publish_ed_desc, publish_text_num)
    }

    override fun initEvent() {

        btSubMit.setOnClickListener {

            if (!TextUtils.isEmpty(publish_ed_desc.text)) {
                mPresenter.subMitAdv(publish_ed_desc.text.toString())
            } else {
                ToastUtils.showInfo("请输入反馈内容")
            }


        }

    }
}