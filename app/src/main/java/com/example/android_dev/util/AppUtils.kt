package com.example.android_dev.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AppUtils {
    fun hideKeyboard(context: Context?, view: View?) {
        context?.run {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            if (inputManager != null && view != null) {
                inputManager.hideSoftInputFromWindow (view.windowToken, 0)
            }
        }
    }
}

