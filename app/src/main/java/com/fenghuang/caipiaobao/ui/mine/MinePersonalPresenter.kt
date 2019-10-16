package com.fenghuang.caipiaobao.ui.mine

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.CameraUtils
import com.fenghuang.caipiaobao.widget.IosBottomListWindow


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/15- 18:09
 * @ Describe
 *
 */

class MinePersonalPresenter : BaseMvpPresenter<MinePersonalFragment>() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getPhotoFromPhone(activity: Activity) {
        IosBottomListWindow(activity)
                .setTitle("添加头像")
                .setItem("拍摄", ViewUtils.getColor(R.color.black)) {
                    checkPermissionAndCamera(activity)
                }
                .setItem("从手机相册选择") {
                    getPicFromAlbum()
                }
                .setCancelButton("取消")
                .show()
    }


    //相机操作
    fun getPicFromCamera(context: Context) {
        CameraUtils.openCamera(context, mView)
    }

    //相册
    private fun getPicFromAlbum() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        photoPickerIntent.type = "image/*"
        mView.startActivityForResult(photoPickerIntent, 0x11)
    }

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private fun checkPermissionAndCamera(activity: Activity) {
        val hasCameraPermission = ContextCompat.checkSelfPermission(activity,
                permission.CAMERA)
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            getPicFromCamera(activity)
        } else {
            val permission = arrayOf(permission.CAMERA)
            mView.requestPermissions(permission,
                    0x000)
        }
    }

}