package com.ntechnology.ddinfo.ui.widgets

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.models.GradeResultModel
import kotlinx.android.synthetic.main.layout_grade_result.view.*


class GradeResultView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    var gradeResultModel: ArrayList<GradeResultModel>? = null
        set(value) {
            field = value
            bindGradeResult()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_grade_result, this)
    }

    private fun bindGradeResult() {
        errorLL.visibility = View.GONE
        lgr_ll.visibility = View.VISIBLE
        lgr_ll.removeAllViews()
        gradeResultModel?.let {
            for (gradeResult in it) {

                val title = TextView(context)
                title.apply {
                    layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    setTextColor(ResourcesCompat.getColor(context.resources, R.color.textColorWhite, null))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    text = gradeResult.title

                }


                val value = TextView(context)
                value.apply {
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    setTextColor(ResourcesCompat.getColor(context.resources, R.color.textColorWhite, null))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    gravity = Gravity.END
                    text = gradeResult.value
                }


                val ll = LinearLayout(context)

                ll.orientation = LinearLayout.HORIZONTAL

                val lp = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

                val d8dpInPx =
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()

                lp.setMargins(d8dpInPx, d8dpInPx, d8dpInPx, 0)

                ll.layoutParams = lp

                ll.addView(title)
                ll.addView(value)


                lgr_ll.addView(ll)

            }
        }

    }

    fun showError() {
        errorLL.visibility = View.VISIBLE
        lgr_ll.visibility = View.GONE
    }
}