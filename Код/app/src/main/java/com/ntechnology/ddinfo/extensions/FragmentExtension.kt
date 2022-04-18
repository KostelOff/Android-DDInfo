package com.ntechnology.ddinfo.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun Fragment.getBackStackName(): String {
    return javaClass.canonicalName.orEmpty()
}

fun FragmentManager.replace(containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
    beginTransaction().apply {
        replace(containerId, fragment, fragment.getBackStackName())
        if (addToBackStack) {
            addToBackStack(fragment.getBackStackName())
        }
    }.commit()
}