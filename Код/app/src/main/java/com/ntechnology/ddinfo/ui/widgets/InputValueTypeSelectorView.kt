package com.ntechnology.ddinfo.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.ntechnology.ddinfo.R
import com.ntechnology.ddinfo.extensions.getCompatColor
import com.ntechnology.ddinfo.extensions.setBackgroundColorFromRes
import kotlinx.android.synthetic.main.layout_input_value_type_selector.view.*

class InputValueTypeSelectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var type: String = INPUT_TYPE_PERCENT
        set(value) {
            field = value
            applySelection(percentTextView, value == INPUT_TYPE_PERCENT)
            applySelection(currencyTextView, value == INPUT_TYPE_CURRENCY)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_input_value_type_selector, this)

        percentTextView.setOnClickListener {
            setPercentType()
        }

        currencyTextView.setOnClickListener {
            setCurrencyType()
        }

        type = DEFAULT_INPUT_TYPE
    }

    fun getInputValueType(): String {
        return type
    }

    fun setPercentType() {
        type = INPUT_TYPE_PERCENT
    }

    fun setCurrencyType() {
        type = INPUT_TYPE_CURRENCY
    }

    private fun applySelection(view: TextView, isSelected: Boolean) {
        view.setBackgroundColorFromRes(if (isSelected) R.color.colorSelector else R.color.colorWhite)
        view.setTextColor(context.getCompatColor(if (isSelected) R.color.textColorWhite else R.color.textColorPrimary))
    }

    companion object {
        const val INPUT_TYPE_PERCENT = "percent"
        const val INPUT_TYPE_CURRENCY = "currency"

        private const val DEFAULT_INPUT_TYPE = INPUT_TYPE_PERCENT
    }
}