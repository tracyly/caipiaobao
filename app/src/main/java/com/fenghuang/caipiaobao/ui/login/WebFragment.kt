package com.fenghuang.caipiaobao.ui.login

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.helper.RxPermissionHelper
import com.fenghuang.caipiaobao.ui.widget.mywebview.ZpImageUtils
import com.fenghuang.caipiaobao.ui.widget.mywebview.ZpWebChromeClient
import com.fenghuang.caipiaobao.widget.IosBottomListWindow
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.fragment_bet.*
import java.io.File

/**
 *
 * @ Author  QinTian
 * @ Date  2019/8/28- 11:29
 * @ Describe 投注页
 *
 */
open class WebFragment : BaseMvpFragment<WebPresenter>() {

    var baseUrl: String? = null

    override fun getPageTitle() = "乐购用户直播协议"

    override fun attachView() = mPresenter.attachView(this)

    override fun isOverridePage() = false

    override fun attachPresenter() = WebPresenter()

    override fun getContentResID() = R.layout.fragment_bet

    override fun isShowBackIconWhite() = false




    override fun initContentView() {
    }

    override fun initData() {
        initWeb()
        baseBetWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://") ||
                            url.startsWith("mailto://") || url.startsWith("tel://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }//其他自定义的scheme
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false
                }

                return false
            }
        }
        mPresenter.getUrl()

    }

    override fun initEvent() {
    }


    private var mUploadMsg: ValueCallback<Uri>? = null
    private var mUploadMsgs: ValueCallback<Array<Uri>>? = null
    private fun initWeb() {
        baseBetWebView.setOpenFileChooserCallBack(object : ZpWebChromeClient.OpenFileChooserCallBack {
            override fun openFileChooserCallBack(uploadMsg: ValueCallback<Uri>, acceptType: String) {
                mUploadMsg = uploadMsg
                checkPremission(0, null)
            }

            override fun showFileChooserCallBack(filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: WebChromeClient.FileChooserParams) {
                if (mUploadMsgs != null) {
                    mUploadMsgs!!.onReceiveValue(null)
                }
                mUploadMsgs = filePathCallback
                checkPremission(1, fileChooserParams)
            }
        })
    }


    /**
     * 选择图片弹框
     */
    val REQUEST_SELECT_FILE_CODE = 100
    private val REQUEST_FILE_CHOOSER_CODE = 101
    private val REQUEST_FILE_CAMERA_CODE = 102
    // 相机拍照返回的图片文件
    private var mFileFromCamera: File? = null
    // 默认图片压缩大小（单位：K）
    val IMAGE_COMPRESS_SIZE_DEFAULT = 400
    // 压缩图片最小高度
    val COMPRESS_MIN_HEIGHT = 900
    // 压缩图片最小宽度
    val COMPRESS_MIN_WIDTH = 675
    var dialog: IosBottomListWindow? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showSelectPictrueDialog(tag: Int, fileChooserParams: WebChromeClient.FileChooserParams?) {
        dialog = IosBottomListWindow(getPageActivity())
        dialog!!
                .setItem("拍照") { takeCameraPhoto() }
                .setItem("相册") {
                    if (tag == 0) {
                        val i = Intent(Intent.ACTION_GET_CONTENT)
                        i.addCategory(Intent.CATEGORY_OPENABLE)
                        i.type = "*/*"
                        startActivityForResult(Intent.createChooser(i, "File Browser"), REQUEST_FILE_CHOOSER_CODE)
                    } else {
                        try {
                            val intent = fileChooserParams?.createIntent()
                            startActivityForResult(intent, REQUEST_SELECT_FILE_CODE)
                        } catch (e: ActivityNotFoundException) {
                            mUploadMsgs = null
                        }

                    }
                }
                .setTitle("选择图片")
                .setCancelButton("取消")
        dialog?.setOnDissMissClickListener {
            if (mUploadMsgs != null) {
                mUploadMsgs?.onReceiveValue(null)
                mUploadMsgs = null
            }
        }
        dialog?.setCancelButtonClickListener {
            if (mUploadMsgs != null) {
                mUploadMsgs?.onReceiveValue(null)
                mUploadMsgs = null
            }
        }
        dialog!!.show()
    }

    fun takeCameraPhoto() {
        mFileFromCamera = File(Environment.getExternalStorageDirectory().path + "/" + System.currentTimeMillis() + ".jpg")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imgUrl: Uri
        if (getPageActivity().applicationInfo.targetSdkVersion > Build.VERSION_CODES.M) {
            val authority = "com.fenghuang.caipiaobao.ui.widget.mywebview.UploadFileProvider"
            imgUrl = FileProvider.getUriForFile(getPageActivity(), authority, mFileFromCamera!!)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            imgUrl = Uri.fromFile(mFileFromCamera)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl)
        startActivityForResult(intent, REQUEST_FILE_CAMERA_CODE)
    }

    //检测权限

    fun checkPremission(tag: Int, fileChooserParams: WebChromeClient.FileChooserParams?) {
        if (RxPermissionHelper.checkPermission(android.Manifest.permission.CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showSelectPictrueDialog(tag, fileChooserParams)
            }
        } else {
            if (RxPermissionHelper.request(this, android.Manifest.permission.CAMERA).isDisposed) {

            } else {
                if (mUploadMsgs != null) {
                    mUploadMsgs?.onReceiveValue(null)
                    mUploadMsgs = null
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_SELECT_FILE_CODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mUploadMsgs == null) {
                        return
                    }
                    mUploadMsgs?.onReceiveValue(android.webkit.WebChromeClient.FileChooserParams.parseResult(resultCode, data))
                    mUploadMsgs = null
                }
                if (dialog != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dialog!!.dismiss()
                    }
                }
            }
            REQUEST_FILE_CHOOSER_CODE -> {
                if (mUploadMsg == null) {
                    return
                }
                val result = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
                mUploadMsg?.onReceiveValue(result)
                mUploadMsg = null
            }
            REQUEST_FILE_CAMERA_CODE -> {
                takePictureFromCamera()
                if (dialog != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dialog!!.dismiss()
                    }
                }
            }
        }
    }


    /**
     * 处理相机返回的图片
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun takePictureFromCamera() {
        if (mFileFromCamera != null && mFileFromCamera?.exists()!!) {
            val filePath = mFileFromCamera?.absolutePath
            // 压缩图片到指定大小
            val imgFile = ZpImageUtils.compressImage(getPageActivity(), filePath, COMPRESS_MIN_WIDTH, COMPRESS_MIN_HEIGHT, IMAGE_COMPRESS_SIZE_DEFAULT)

            val localUri = Uri.fromFile(imgFile)
            val localIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri)
            getPageActivity().sendBroadcast(localIntent)
            val result = Uri.fromFile(imgFile)

            if (mUploadMsg != null) {
                mUploadMsg?.onReceiveValue(Uri.parse(filePath))
                mUploadMsg = null
            }
            if (mUploadMsgs != null) {
                mUploadMsgs?.onReceiveValue(arrayOf(result))
                mUploadMsgs = null
            }
        }
    }

}