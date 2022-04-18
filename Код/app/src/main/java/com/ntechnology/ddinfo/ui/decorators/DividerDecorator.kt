package com.ntechnology.ddinfo.ui.decorators

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import androidx.core.view.children
import com.ntechnology.ddinfo.R

class DividerDecorator(private val context: Context?) : RecyclerView.ItemDecoration() {

    private val dividerDrawable by lazy {
        context?.let { ContextCompat.getDrawable(context, R.drawable.shape_divider) }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        dividerDrawable?.let { drawable ->
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            parent.children.forEach { child ->
                val top = child.bottom - drawable.intrinsicHeight
                val bottom = child.bottom

                drawable.setBounds(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }
}