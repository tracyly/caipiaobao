package com.fenghuang.caipiaobao.ui.lottery

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.header.material.MaterialHeader
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.LotteryViewTypeConstant
import com.fenghuang.caipiaobao.ui.home.HomeDelegateAdapter
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryOpenCodeDataBean
import com.fenghuang.caipiaobao.ui.lottery.data.LotterySettingChooseDataBean
import com.yc.cn.ycbaseadapterlib.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home_new.*
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:21
 * @ Describe  开奖页面
 *
 */

class LotteryFragment : BaseMvpFragment<LotteryPresenter>() {

    // 存放各个模块的适配器
    private var mAdapters: MutableList<DelegateAdapter.Adapter<*>>? = null
    private var mDelegateAdapter: DelegateAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun getPageTitle() = getString(R.string.lottery_open)

    override fun attachPresenter() = LotteryPresenter()

    override fun getContentResID() = R.layout.fragment_lottery

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun initContentView() {
        super.initContentView()
//        loadRootFragment(R.id.linLotteryType, LotteryTypeFragment())
        mAdapters = LinkedList()
        initRecycler()
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context!!))
    }

    override fun initData() {
        mPresenter.getLotteryType()
        initMoreView()
        mDelegateAdapter?.setAdapters(mAdapters)
    }

    /**
     * 初始化Recycler
     */
    private fun initRecycler() {
        val layoutManager = VirtualLayoutManager(getPageActivity())
        recyclerView.layoutManager = layoutManager
        // 设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        val viewPool = RecyclerView.RecycledViewPool()
        recyclerView.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0, 20)
        mDelegateAdapter = DelegateAdapter(layoutManager, true)
        recyclerView.adapter = mDelegateAdapter

    }

    /**
     * 彩种
     */
    fun initLotteryType(data: List<LotteryDataBean>?) {
        val lotteryTypeAdapter = object : LotteryDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_lottery_type, 1, LotteryViewTypeConstant.TYPE_LOTTERY) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                val recyclerView = holder.getView<RecyclerView>(R.id.rvLotteryType)
                recyclerView.layoutManager = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
                val lotteryTypeAdapter = LotteryTypeAdapter(getPageActivity())
                lotteryTypeAdapter.addAll(data)
                recyclerView.adapter = lotteryTypeAdapter
            }

        }
        mAdapters?.add(lotteryTypeAdapter)

    }


    /**
     *  开奖记录 分析工具
     */
    fun initLotteryInfo(data: List<LotteryOpenCodeDataBean>?, item: List<LotterySettingChooseDataBean>?) {
        val lotteryTypeAdapter = object : LotteryDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_lottery_info, 1, LotteryViewTypeConstant.TYPE_LOTTERY_HISTORY) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                val rvOpenCode = holder.getView<RecyclerView>(R.id.rvOpenCode)
                rvOpenCode.layoutManager = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
                val lotteryTypeAdapter = LotteryOpenCodeAdapter(getPageActivity())
                lotteryTypeAdapter.addAll(data)
                rvOpenCode.adapter = lotteryTypeAdapter

                val rvLotterySettingChoose = holder.getView<RecyclerView>(R.id.rvSettingChoose)
                rvLotterySettingChoose.layoutManager = GridLayoutManager(getPageActivity(), 4, RecyclerView.VERTICAL, false)
                val lotterySettingChooseAdapter = LotterySettingChoose(getPageActivity())
                lotterySettingChooseAdapter.addAll(item)
                rvLotterySettingChoose.adapter = lotterySettingChooseAdapter
            }
        }
        mAdapters?.add(lotteryTypeAdapter)
    }


    /**
     *  专家计划
     */
    fun expertPlan() {
        val expertPlanAdapter = object : LotteryDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_expert_plan, 5, LotteryViewTypeConstant.TYPE_PLAN) {

        }
    }


    private fun initMoreView() {
        val moreAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_more, 1, LotteryViewTypeConstant.TYPE_BOTTOM) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                holder.setText(R.id.tvHomeMore, getString(R.string.home_list_more))
            }
        }
        mAdapters?.add(moreAdapter)
    }

}