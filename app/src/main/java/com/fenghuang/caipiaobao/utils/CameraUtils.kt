package com.fenghuang.caipiaobao.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import androidx.fragment.app.Fragment
import com.fenghuang.baselib.utils.AppUtils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/16- 13:45
 * @ Describe
 *
 */

object CameraUtils {

    /**
     * 调起相机拍照
     */
    //用于保存拍照图片的uri
    var mCameraUri: Uri? = null
    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    var mCameraImagePath: String? = null
    // 是否是Android 10以上手机
    @SuppressLint("ObsoleteSdkInt")
    val isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1

    fun openCamera(context: Context, fragment: Fragment) {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 判断是否有相机
        if (captureIntent.resolveActivity(context.packageManager) != null) {
            var photoFile: File? = null
            var photoUri: Uri? = null
            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri(context)
            } else {
                try {
                    photoFile = createImageFile(context)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.absolutePath
                    photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        FileProvider.getUriForFile(context, AppUtils.getPackageName() + ".fileProvider", photoFile)
                    } else {
                        Uri.fromFile(photoFile)
                    }
                }
            }
            mCameraUri = photoUri
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                fragment.startActivityForResult(captureIntent, 0x12)
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private fun createImageUri(context: Context): Uri {
        val status = Environment.getExternalStorageState()
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        val values = ContentValues()
        return if (status == MEDIA_MOUNTED) {
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) as Uri
        } else {
            context.contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values) as Uri
        }
    }

    /**
     * 创建保存图片的文件
     */
    @Throws(IOException::class)
    private fun createImageFile(context: Context): File? {
        val imageName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir?.exists()!!) {
            storageDir.mkdir()
        }
        val tempFile = File(storageDir, imageName)
        return if (MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            null
        } else tempFile
    }


    /**
     * 裁剪图片
     * @param
     */
    var imageCropUri: Uri? = null

    fun cropImg(uri: Uri, fragment: Fragment) {
        val cropImage = File(Environment.getExternalStorageDirectory(), "crop_image.jpg")
        try {
            if (cropImage.exists()) {
                cropImage.delete()
            }
            cropImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageCropUri = Uri.fromFile(cropImage)
        val intent = Intent("com.android.camera.action.CROP")  //调用裁剪
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)  //设置宽高比例为1：1
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 700)   //设置裁剪图片宽高700x700
        intent.putExtra("outputY", 700)
        intent.putExtra("return-data", false)
        intent.putExtra("scale", true)
        intent.putExtra("scaleUpIfNeeded", true)   //防止出现黑边框
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())  //设置输出格式
        intent.putExtra("noFaceDetection", true)
        fragment.startActivityForResult(intent, 0x13)
    }

    fun getRealFilePath(uri: Uri?, context: Context): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

}