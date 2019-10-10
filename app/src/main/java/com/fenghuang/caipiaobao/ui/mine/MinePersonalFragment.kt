package com.fenghuang.caipiaobao.ui.mine

import android.os.Build
import androidx.annotation.RequiresApi
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.widget.IosBottomListWindow
import kotlinx.android.synthetic.main.fragment_presonal.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 17:46
 * @ Describe 个人资料
 *
 */

class MinePersonalFragment : BaseNavFragment() {


    override fun getContentResID() = R.layout.fragment_presonal

    override fun getPageTitle() = getString(R.string.mine_contact_personal)

    override fun isShowBackIconWhite() = false


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        ImageManager.loadRoundFrameUserLogo("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", findView(R.id.imgUserPhoto), getColor(R.color.grey_dd))
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initEvent() {
        imgSetPhoto.setOnClickListener {
            IosBottomListWindow(getPageActivity())
                    .setTitle("添加头像")
                    .setItem("拍摄", getColor(R.color.black)) {
                    }
                    .setItem("从手机相册选择") {
                    }
                    .setCancelButton("取消")
                    .show()

        }
    }
}