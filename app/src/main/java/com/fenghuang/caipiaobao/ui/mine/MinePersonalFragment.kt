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
import com.fenghuang.caipiaobao.utils.UserInfoSp
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
        ImageManager.loadRoundFrameUserLogo(UserInfoSp.getUserPhoto(), imgUserPhoto, 12, getColor(R.color.white))
        edUserName.setText(UserInfoSp.getUserNickName())
        edUserSex.setText(if (UserInfoSp.getUserSex() == 1) "男" else "女")
        publish_ed_desc.setText(UserInfoSp.getUserProfile())
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initEvent() {
        imgSetPhoto.setOnClickListener {
            mPresenter.getPhotoFromPhone(getPageActivity())
        }
        btUpLoadUserInfo.setOnClickListener {
            mPresenter.upLoadPersonalInfo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x11) {
            cropImg(data?.data as Uri, this)
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
            ImageManager.loadRoundFromBitmap(bitmap, findView(R.id.imgUserPhoto), getColor(R.color.grey_dd))
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
                        ToastUtils.showInfo("同意权限")
                    } else {
                        ToastUtils.showInfo("拍照权限被拒绝,请到设置中打开此权限")
                    }
                }
            }
        }
    }

}