package com.ntechnology.ddinfo.extensions

import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorRes
import android.view.View

fun View.setBackgroundColorFromRes(@ColorRes colorRes: Int) {
    background = ColorDrawable(context.getCompatColor(colorRes))
}