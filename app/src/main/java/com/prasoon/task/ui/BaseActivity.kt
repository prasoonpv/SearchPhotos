package com.prasoon.task.ui

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.prasoon.task.R
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity() {

    private var viewModel: T? = null
    var alertDialog: Dialog? = null

    /**
     * @return view model instance
     */
    abstract fun getViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (viewModel == null) getViewModel() else viewModel
        alertDialog = Dialog(this)
    }


    fun setToolBar(toolbar: Toolbar, name: String) {
        toolbar.title = name
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialog(
        title: String, message: String, isNegativeButtonAvailable: Boolean,
        positiveClickListener: View.OnClickListener,
        negativeClickListener: View.OnClickListener
    ) {
        if (alertDialog != null) {
            if (alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
        } else {
            alertDialog = Dialog(this)
            alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        alertDialog!!.setCancelable(false)
        alertDialog!!.setContentView(R.layout.custom_alert_dialog)

        val tvTitle = alertDialog!!.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = title

        val tvMessage = alertDialog!!.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message

        val llbutton = alertDialog!!.findViewById<LinearLayout>(R.id.ll_button)
        val btnOkay = alertDialog!!.findViewById<Button>(R.id.btn_okay)
        if (isNegativeButtonAvailable) {
            llbutton.visibility = View.VISIBLE
            btnOkay.visibility = View.GONE
            val btnYes = alertDialog!!.findViewById<Button>(R.id.btn_yes)
            val btnNo = alertDialog!!.findViewById<Button>(R.id.btn_no)
            btnYes.setOnClickListener(positiveClickListener)
            btnNo.setOnClickListener(negativeClickListener)
        } else {
            llbutton.visibility = View.GONE
            btnOkay.visibility = View.VISIBLE
            btnOkay.setOnClickListener(positiveClickListener)
        }

        alertDialog!!.show()
    }

}