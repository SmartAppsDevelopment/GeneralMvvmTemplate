package com.example.templatesampleapp.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Umer Bilal
 * Created 5/20/2023 at 3:44 PM
 */

fun Context.copyText(text: String) {
    showToast("Copied")
    val clipboard = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.shareText(text: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, text)
    sendIntent.type = "text/plain"
    startActivity(sendIntent)
}
