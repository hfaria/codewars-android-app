package com.hfaria.portfolio.codewars.util

import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun short(ctx: Context, text: String) {
        Toast
            .makeText(ctx, text, Toast.LENGTH_SHORT)
            .show()
    }

    fun long(ctx: Context, text: String) {
        Toast
            .makeText(ctx, text, Toast.LENGTH_LONG)
            .show()
    }
}