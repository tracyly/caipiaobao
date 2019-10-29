package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
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

    //输入限制
    fun initEditPersonal(editText: EditText, textView: TextView) {
        val num = 0
        val mMaxNum = 50
        editText.addTextChangedListener(object : TextWatcher {
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
                textView.text = "$number/50"
                selectionStart = editText.selectionStart
                selectionEnd = editText.selectionEnd
                //判断大于最大值
                if (wordNum!!.length > mMaxNum) {
                    s.delete(selectionStart - 1, selectionEnd)
                    val tempSelection = selectionEnd
                    editText.text = s
                    editText.setSelection(tempSelection)
                    ToastUtils.showInfo("最多输入50字")
                }
            }
        })
    }

    //上传个人资料
    fun upLoadPersonalInfo(nickName: String, gender: Int, profile: String) {
        MineApi.upLoadPersonalInfo(nickName, gender, profile) {
            onSuccess {
                ToastUtils.showSuccess("修改成功")
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }

        }
    }

}