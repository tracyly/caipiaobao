package com.fenghuang.caipiaobao.ui.quiz

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.fragment.BaseContentFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.quiz.CheckPhoto.MyImageAdapter
import kotlinx.android.synthetic.main.fragment_quiz_check_img.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 14:54
 * @ Describe
 *
 */

class QuizCheckImgFragment : BaseContentFragment() {


    var mCurrentIndex: Int = 0

    var urlList = arrayListOf<String>()

    override fun getLayoutResID() = R.layout.fragment_quiz_check_img


    override fun initContentView() {
        mCurrentIndex = arguments?.getInt("position") ?: 0
        urlList = arguments?.getStringArrayList("urlList")!!
        val mAdapters = MyImageAdapter(this, urlList, View.OnClickListener { this.pop() })
        viewPagers.adapter = mAdapters
        viewPagers.currentItem = mCurrentIndex
    }

    override fun initData() {
        getCircle()
        ll_container.getChildAt(mCurrentIndex).isEnabled = true
    }

    override fun initEvent() {
        viewPagers.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                ll_container.getChildAt(position).isEnabled = true
                ll_container.getChildAt(mCurrentIndex).isEnabled = false
                mCurrentIndex = position
            }

        })
    }

    companion object {
        private lateinit var mBundle: Bundle
        fun newInstance(urlList: ArrayList<String>, position: Int): QuizCheckImgFragment {
            val fragment = QuizCheckImgFragment()
            mBundle = Bundle()
            mBundle.putStringArrayList("urlList", urlList)
            mBundle.putInt("position", position)
            fragment.arguments = mBundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    private fun getCircle() {
        var view: View
        var isp = 0
        while (isp < urlList.size) {
            view = View(context)
            view.setBackgroundResource(R.drawable.round_point)
            view.isEnabled = false
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(30, 30)
            if (isp != 0) {
                params.leftMargin = 10
            }
            ll_container.addView(view, params)
            isp++
        }
    }
}