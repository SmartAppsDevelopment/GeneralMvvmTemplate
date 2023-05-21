package com.example.templatesampleapp.helper

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.templatesampleapp.BuildConfig

/**
 * @author Umer Bilal
 * Created 5/19/2023 at 8:41 PM
 */

fun EditText.getTrimmedText(): String {
    return this.text.toString().trim()
}

fun Fragment.showToast(msg:String){
    this.context?.showToast(msg)
}

fun Fragment.showToastTemp(msg:String){
    this.context?.showToastTemp(msg)
}

fun View.showToast(msg:String){
    this.context.showToast(msg)
}

fun View.showToastTemp(msg:String){
    this.context.showToastTemp(msg)
}

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToastTemp(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun onlyForDebug(callback: () -> Unit) {
    if (BuildConfig.DEBUG)
        callback.invoke()
}