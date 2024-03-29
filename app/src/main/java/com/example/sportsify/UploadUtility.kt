package com.mvp.handyopinion

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException


class UploadUtility(activity: Activity) {

    var activity = activity;
    var dialog: ProgressDialog? = null
    var serverURL: String = "http://10.100.219.14:8000/uploadfile"
    var serverUploadDirectoryPath: String = "http://10.100.219.14:8000/uploadfile"
    val client : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .callTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .build()

    fun uploadFile(sourceFile: File, callback: (JSONObject) -> Unit) {
        Thread {
            val mimeType = "video/mp4"
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = sourceFile.name
            toggleProgressDialog(true)
            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file", fileName, sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        toggleProgressDialog(false)
                        Log.e("File upload", "failed")
                        showToast("File uploading failed")
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        toggleProgressDialog(false)
                        val jsonData = response.body?.string()
                        if (response.isSuccessful) {
                            var json = JSONObject(jsonData)
                            Log.d(
                                "File upload",
                                "success, path: $serverUploadDirectoryPath$fileName"
                            )
                            showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                            callback(json)
                        } else {
                            Log.e("File upload", "no object found")
                            showToast("No object detected")
                        }
                    }
                })
                // val centered_object = json.get["centered_object"]
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
        }.start()
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText( activity, message, Toast.LENGTH_LONG ).show()
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if (show) {
                dialog = ProgressDialog.show(activity, "", "Uploading file...", true);
            } else {
                dialog?.dismiss();
            }
        }
    }

}