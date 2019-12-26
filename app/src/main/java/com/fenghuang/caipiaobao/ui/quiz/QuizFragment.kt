package com.fenghuang.caipiaobao.ui.quiz

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean
import com.pingerx.banner.BannerView
import com.pingerx.banner.holder.BannerHolderCreator
import kotlinx.android.synthetic.main.fragment_quiz.*


/**
 *  author : Peter
 *  date   : 2019/10/17 20:20
 *  desc   : 竟猜
 */
class QuizFragment : BaseMvpFragment<QuizPresenter>() {

    var mPage = 0
    var adapter: QuizAdapter? = null
    var list: List<QuizResponse>? = null

    private lateinit var mQuizHolder: QuizHolder

    override fun getPageTitle() = getString(R.string.tab_quiz)

    override fun attachPresenter() = QuizPresenter()

    override fun attachView() = mPresenter.attachView(this)

//    override fun attachAdapter() = QuizAdapter(getPageActivity())

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun getContentResID() = R.layout.fragment_quiz

//    override fun getItemDivider(): RecyclerView.ItemDecoration? {
//        return DividerItemDecoration(getColor(R.color.color_f7f7f7), com.fenghuang.baselib.utils.ViewUtils.dp2px(10))
//    }

    override fun initContentView() {
        //加载默认数据------------------------
        baseInitBanner()
        baseInitRecycle()
        //加载默认数据------------------------end
        mPresenter.loadData(mPage)
        mPresenter.getBanner()
        smartRefreshLayoutQuiz?.setOnRefreshListener {
            mPage = 0
            mPresenter.loadData(mPage)
        }
        smartRefreshLayoutQuiz?.setOnLoadMoreListener {
            mPage++
            mPresenter.loadMore(mPage)
        }
    }

    //默认Recycle---
    private fun baseInitRecycle() {
        val list: List<QuizResponse>
        val imgList: List<String>
        list = ArrayList()
        imgList = ArrayList()
        for (index in 1..3) {
            imgList.add("-1")
        }
        for (index in 1..6) {
            list.add(QuizResponse(-100000000000, 0, imgList, "", 0, "", "", "", "正在加载数据...", 0))
        }
        adapter = QuizAdapter(getPageActivity(), mPresenter)
        recyclerViewQuizList.adapter = adapter
        val value = object : LinearLayoutManager(getPageActivity()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerViewQuizList.layoutManager = value
        (recyclerViewQuizList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter?.addAll(list)
    }

    fun initRecycle(list: List<QuizResponse>) {
        adapter = QuizAdapter(getPageActivity(), mPresenter)
        recyclerViewQuizList.adapter = adapter
        val value = object : LinearLayoutManager(getPageActivity()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerViewQuizList.layoutManager = value
        (recyclerViewQuizList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter?.addAll(list)
    }

    fun addItem(list: List<QuizResponse>) {
        adapter?.addAll(list)
    }

//    override fun initPageView() {
//        super.initPageView()
//        mQuizHolder = QuizHolder()
//        register(QuizResponse::class.java, mQuizHolder)
//        register(QuizTitleBean::class.java, QuizTitleHolder())
//        register(QuizTopImageBean::class.java, QuizTopImageHolder())
//    }

    override fun initEvent() {

    }


    //--------默认Banner-----------
    private fun baseInitBanner() {
        val list: List<QuizTopImageBean>
        list = ArrayList()
        for (index in 1..3) {
            list.add(QuizTopImageBean(""))
        }
        val mBannerView = findView<BannerView<QuizTopImageBean>>(R.id.mQuizBannerViews)
        mBannerView.setPages(list, object : BannerHolderCreator<QuizTopImageBean, QuizBannerHolder> {
            override fun onCreateBannerHolder(): QuizBannerHolder {
                return QuizBannerHolder(mBannerView.getPageMode() == BannerView.PageMode.NORMAL)
            }
        })
    }

    fun initBanner(data: List<QuizTopImageBean>?) {
        val mBannerView = findView<BannerView<QuizTopImageBean>>(R.id.mQuizBannerViews)
        mBannerView.setPages(data, object : BannerHolderCreator<QuizTopImageBean, QuizBannerHolder> {
            override fun onCreateBannerHolder(): QuizBannerHolder {
                return QuizBannerHolder(mBannerView.getPageMode() == BannerView.PageMode.NORMAL)
            }
        })
    }

    //--------默认Banner-----------
    fun notifyQuizHolder(bean: QuizResponse, articleId: Int, clickPosition: Int) {
        adapter?.notifyDataSetChanged()
    }

}