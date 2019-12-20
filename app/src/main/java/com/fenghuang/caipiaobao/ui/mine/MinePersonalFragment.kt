package com.fenghuang.caipiaobao.ui.mine

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.utils.CameraUtils
import com.fenghuang.caipiaobao.utils.CameraUtils.cropImg
import com.fenghuang.caipiaobao.utils.CameraUtils.getRealFilePath
import com.fenghuang.caipiaobao.utils.CameraUtils.imageCropUri
import com.fenghuang.caipiaobao.utils.CameraUtils.mCameraImagePath
import com.fenghuang.caipiaobao.utils.CameraUtils.mCameraUri
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.widget.IosBottomListWindow
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_mine_presonal.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 17:46
 * @ Describe 个人资料
 *
 */

class MinePersonalFragment : BaseMvpFragment<MinePersonalPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MinePersonalPresenter()

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fragment_mine_presonal

    override fun getPageTitle() = getString(R.string.mine_contact_personal)

    override fun isShowBackIconWhite() = false

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        mPresenter.initEditPersonal(publish_ed_desc, publish_text_num)

    }

    override fun initData() {
        mPresenter.getMoney()
        mPresenter.getUserInfo()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initEvent() {
        imgSetPhoto.setOnClickListener {
            mPresenter.getPhotoFromPhone(getPageActivity())
        }
        btUpLoadUserInfo.setOnClickListener {
            if (FastClickUtils.isFastClick()) {

                mPresenter.upLoadPersonalInfo(edUserName.text.toString(), if (edUserSex.text.toString() == "男") 1 else 0, publish_ed_desc.text.toString())
            }
        }

        edUserSex.setOnClickListener {
            IosBottomListWindow(getPageActivity())
                    .setTitle("选择性别")
                    .setItem("男") {
                        edUserSex.text = "男"
                    }
                    .setItem("女") {
                        edUserSex.text = "女"
                    }
                    .setCancelButton("取消")
                    .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x11) {
            if (data?.data != null) {
                cropImg(data.data as Uri, this)
            }
        } else if (requestCode == 0x12) {
            if (resultCode == RESULT_OK) {
                if (CameraUtils.isAndroidQ) {
                    cropImg(mCameraUri as Uri, this)
                } else {
                    cropImg(mCameraImagePath as Uri, this)
                }
            }
        } else if (requestCode == 0x13) {
            val bitmap = BitmapFactory.decodeFile(getRealFilePath(imageCropUri, getPageActivity()))
            if (bitmap != null) {
                ImageManager.loadRoundFromBitmap(bitmap, findView(R.id.imgUserPhoto), getColor(R.color.grey_dd))
                mPresenter.upLoadPersonalAvatar(bitmap)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 0x000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getPicFromCamera(getPageActivity())
            } else {
                val rxPermissions = RxPermissions(this)
                rxPermissions.request(Manifest.permission.CAMERA).subscribe { aBoolean ->
                    if (aBoolean!!) {
//                        ToastUtils.showInfo("同意权限")
                    } else {
                        ToastUtils.showInfo("拍照权限被拒绝,请到设置中打开此权限")
                    }
                }
            }
        }
    }


}