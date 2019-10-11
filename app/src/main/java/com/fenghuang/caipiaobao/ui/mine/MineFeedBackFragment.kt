package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_mine_presonal.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 14:50
 * @ Describe 用户反馈
 *
 */

class MineFeedBackFragment : BaseNavFragment() {

    private val num = 0
    var mMaxNum = 500

    override fun getContentResID() = R.layout.fragment_mine_feedback

    override fun getPageTitle() = getString(R.string.mine_feed_back)

    override fun isShowBackIconWhite() = false

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initEvent() {
        publish_ed_desc.addTextChangedListener(object : TextWatcher {
            //记录输入的字数
            var wordNum: CharSequence? = null
            var selectionStart: Int = 0
            var selectionEnd: Int = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wordNum = s
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val number = num + s!!.length
                publish_text_num.text = "$number/500"
                selectionStart = publish_ed_desc.selectionStart
                selectionEnd = publish_ed_desc.selectionEnd
                //判断大于最大值
                if (wordNum!!.length > mMaxNum) {
                    s.delete(selectionStart - 1, selectionEnd)
                    val tempSelection = selectionEnd
                    publish_ed_desc.text = s
                    publish_ed_desc.setSelection(tempSelection)
                    ToastUtils.showInfo("最多输入500字")
                }
            }

        })

    }
}