package com.fourtitude.asia.recipe.base

import android.Manifest
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fourtitude.asia.recipe.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity(){
    val PHOTO_PERMISSION =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showAlertDialog(message: String, listenerAlertDialog: OnAlertDialogActionListener?){
        AlertDialog.Builder(this)
            .setTitle("")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("ok") { _: DialogInterface, i: Int ->
                listenerAlertDialog?.onOkClick()
            }.show()
    }

    fun showAlertOkBtnDialog(title: String, message: String, listenerAlertDialog: OnAlertDialogActionListener?){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton("Cancel"){ _: DialogInterface, i: Int -> }
            .setPositiveButton("OK") { _: DialogInterface, i: Int ->
                listenerAlertDialog?.onOkClick()
            }.show()
    }

    fun showSnackBar(view: View, isSuccessMsg: Boolean, msg: String){
        val snackBar = Snackbar
            .make(view, if (msg == "") getString(R.string.try_again) else msg, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(if (isSuccessMsg) applicationContext.getColor(R.color.green_old) else applicationContext.getColor(R.color.red))
        snackBar.show()
    }

    fun hideBackButton(){
        supportActionBar!!.setHomeButtonEnabled(false)    // Disable the button
        supportActionBar!!.setDisplayHomeAsUpEnabled(false) // Remove the left caret
        supportActionBar!!.setDisplayShowHomeEnabled(false)
    }

    fun dipToPixels(dipValue:Float):Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().displayMetrics)
    }


    fun getBitmapFromImageView(view: ImageView): Bitmap {
        return (view.drawable as BitmapDrawable).bitmap
    }

    interface OnAlertDialogActionListener {
        fun onOkClick()
    }
}