package com.fenghuang.caipiaobao.ui.quiz

import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerNavFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTitleBean
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog

/**
 *  author : Peter
 *  date   : 2019/10/17 20:20
 *  desc   : 竟猜
 */
class QuizFragment : BaseMultiRecyclerNavFragment<QuizPresenter>() {

    private lateinit var mQuizHolder: QuizHolder

    override fun getPageTitle() = getString(R.string.tab_quiz)
    override fun attachPresenter() = QuizPresenter()

    override fun attachView() = mPresenter.attachView(this)

    override fun getContentResID() = R.layout.fragment_quiz


//    override fun getItemDivider(): RecyclerView.ItemDecoration? {
//        return DividerItemDecoration(getColor(R.color.color_f7f7f7), com.fenghuang.baselib.utils.ViewUtils.dp2px(10))
//    }

    override fun initPageView() {
        super.initPageView()
        mQuizHolder = QuizHolder()
        register(QuizResponse::class.java, mQuizHolder)
        register(QuizTitleBean::class.java, QuizTitleHolder())
        register(QuizTopImageBean::class.java, QuizTopImageHolder())
    }

    override fun initEvent() {
        mQuizHolder.setOnSendLikeClickListener { id, data ->
            if (data.is_like == 0) {
                data.is_like = 1
            } else data.is_like = 0
            mPresenter.postQuizLike(id)
        }
    }

    fun notifyQuizHolder() {
        mQuizHolder.mMultiTypeAdapter?.notifyDataSetChanged()
    }

    /**
     * 提示登陆
     */
    fun showLoginTips() {
        val tipsConfirmDialog = TipsConfirmDialog(getPageActivity(), "请登录后再操作", "去登录", "下次再说", "")
        tipsConfirmDialog.setConfirmClickListener {
            LaunchUtils.startFragment(getPageActivity(), LoginFragment())
        }
        tipsConfirmDialog.show()
    }
}