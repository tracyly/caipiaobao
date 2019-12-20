package com.fenghuang.caipiaobao.ui.mine

import android.Manifest.permission
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils.getColor
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.lottery.data.UserChangePhoto
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.CameraUtils
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.IosBottomListWindow
import com.google.gson.JsonParser
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_mine_presonal.*
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException


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
                .setItem("拍摄", getColor(R.color.black)) {
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
            mView.showPageLoadingDialog()
            onSuccess {
                mView.hidePageLoadingDialog()
                ToastUtils.showSuccess("信息修改成功")
                RxBus.get().post(UserChangePhoto("", "", "", isUpLoad = false, loadAll = true))
                mView.pop()
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }
        }
    }

    //上传个人头像
    fun upLoadPersonalAvatar(bitmap: Bitmap) {
        mView.showPageLoadingDialog()
        val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("avatar", "data:image/png;base64," + bitMapToBase64(bitmap))
        val requestBody = builder.build()
        val request = Request.Builder()
                .url(MineApi.getBaseUrlMe() + "/userinfo/" + MineApi.USER_UPLOAD_AVATAR)
                .addHeader("Authorization", UserInfoSp.getTokenWithBearer())
                .post(requestBody)
                .build()
        val client = OkHttpClient.Builder().build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (mView.isActive()) {
                    try {
                        val json = JsonParser().parse(response.body()?.string()!!).asJsonObject
                        if (json.get("code").asInt == 1) {
                            RxBus.get().post(UserChangePhoto(json.get("data").asJsonObject.get("avatar").asString, mView.edUserName.text.toString(), mView.publish_ed_desc.text.toString(), true))
                            Looper.prepare()
                            ToastUtils.showSuccess("头像修改成功")
                            mView.hidePageLoadingDialog()
                            Looper.loop()
                        } else {
                            Looper.prepare()
                            ToastUtils.showError(json.get("msg").asString)
                            mView.hidePageLoadingDialog()
                            Looper.loop()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Looper.prepare()
                        ToastUtils.showNormal("服务器异常,上传失败")
                        mView.hidePageLoadingDialog()
                        Looper.loop()
                    }
                    mView.hidePageLoadingDialog()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                ToastUtils.showError(e.toString())
                mView.hidePageLoadingDialog()
            }
        })
    }


    private fun bitMapToBase64(bitmap: Bitmap): String {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)//第二个入参表示图片压缩率，如果是100就表示不压缩
        val bytes = bos.toByteArray()
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }


    //获取用户信息
    fun getUserInfo() {
        if (UserInfoSp.getIsLogin()) {
            mView.showPageLoadingDialog()
            MineApi.getUserInfo {
                onSuccess {
                    if (mView.isActive()) {
                        UserInfoSp.putUserName(it.username)
                        UserInfoSp.putUserPhoto(it.avatar)
                        UserInfoSp.putUserNickName(it.nickname)
                        UserInfoSp.putUserSex(it.gender)
                        UserInfoSp.putUserPhone(it.phone)
                        ImageManager.loadRoundFrameUserLogo(it.avatar, mView.imgUserPhoto, 12, getColor(R.color.white))
                        mView.edUserName.setText(it.nickname)
                        when {
                            it.gender == 1 -> mView.edUserSex.text = "男"
                            it.gender == 0 -> mView.edUserSex.text = "女"
                            else -> mView.edUserSex.text = "未知"
                        }
                        mView.publish_ed_desc.setText(it.profile)
                    }
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    mView.hidePageLoadingDialog()
                    ExceptionDialog.showExpireDialog(mView.requireContext(), it)
                }
            }
        } else {
            ExceptionDialog.showExpireDialog(mView.requireContext())
        }
    }

    /**
     * 获取余额去判断是否被顶下去
     */
    fun getMoney() {
        MineApi.getUserBalance {
            onSuccess { }
            onFailed { ExceptionDialog.showExpireDialog(mView.requireContext(), it) }
        }
    }
}