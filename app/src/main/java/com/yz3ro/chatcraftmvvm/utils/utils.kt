package com.yz3ro.chatcraftmvvm.utils

import android.content.Context
import android.widget.Toast

object utils {
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
    fun logError(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }
}