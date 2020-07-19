package com.prasoon.task.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.prasoon.task.R
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utils @Inject
constructor(var context: Context?) {
    var dialogProgress: Dialog? = null

    val isConnectingToInternet: Boolean
        get() {
            if (context != null) {
                val connMgr = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connMgr.activeNetworkInfo

                return if (networkInfo != null && networkInfo.isConnected)
                    true
                else
                    false
            }

            return false
        }

    fun showSnackbar(context: Activity, message: String) {
        val snackbar = Snackbar.make(
            context.findViewById(android.R.id.content), message,
            Snackbar.LENGTH_LONG
        )
        val view = snackbar.view
        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        view.layoutParams = layoutParams
        view.setBackgroundColor(context.resources.getColor(R.color.colorAccent))

        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(context.resources.getColor(R.color.black))
        snackbar.show()
    }

    fun showSnackbar(context: Activity, message: String, backGroundColor: Int, textColor: Int) {
        val snackbar = Snackbar.make(
            context.findViewById(android.R.id.content), message,
            Snackbar.LENGTH_LONG
        )
        val view = snackbar.view
        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        view.layoutParams = layoutParams
        view.setBackgroundColor(context.resources.getColor(backGroundColor))

        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(context.resources.getColor(textColor))
        snackbar.show()
    }

    fun showProgressDialog(activity: Context) {
        try {
            if (dialogProgress != null) {
                if (dialogProgress!!.isShowing) {
                    dialogProgress!!.dismiss()
                }
            }
            dialogProgress = Dialog(activity)
            dialogProgress!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            (dialogProgress!!.window)?.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            dialogProgress!!.setContentView(R.layout.progress_loading_layout)
            dialogProgress!!.setCancelable(true)
            dialogProgress!!.setCanceledOnTouchOutside(true)
            dialogProgress!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideProgressDialog() {
        if (dialogProgress != null) {
            dialogProgress!!.dismiss()
        }
    }

    fun convertToBase64(capturedImage: Bitmap?): String? {
        if (capturedImage != null) {
            val byteArray = ByteArrayOutputStream()
            capturedImage.compress(Bitmap.CompressFormat.JPEG, 40, byteArray)
            val b = byteArray.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        return null
    }

    companion object {

        var TYPE_WIFI = 1
        var TYPE_MOBILE = 2
        var TYPE_NOT_CONNECTED = 0
        private val TAG = "UtilsClass"
    }
}