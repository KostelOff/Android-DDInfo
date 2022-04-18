package com.ntechnology.ddinfo.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.ntechnology.ddinfo.models.CalculationResultModel
import kotlinx.android.synthetic.main.layout_calculation_result.view.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class CalculationResultView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    var calculationResult: CalculationResultModel? = null
        set(value) {
            field = value
            bindCalculationResult()
        }

    init {
        LayoutInflater.from(context).inflate(com.ntechnology.ddinfo.R.layout.layout_calculation_result, this)
    }

    private fun bindCalculationResult() {
        calculationResult?.let { item ->
            payoffTextView.text =
                if (item.payoffBD == BigDecimal.ZERO) getCurrencyFormat(item.payoff)
                else getCurrencyFormat(item.payoffBD)

            taxesTextView.text =
                if (item.taxesBD == BigDecimal.ZERO) getCurrencyFormat(item.taxes)
                else getCurrencyFormat(item.taxesBD)

            otherObligationTextView.text =
                if (item.otherObligationsBD == BigDecimal.ZERO) getCurrencyFormat(item.otherObligations)
                else getCurrencyFormat(item.otherObligationsBD)

            payoffAfterTaxesTextView.text =
                if (item.payoffBD == BigDecimal.ZERO && item.taxesBD == BigDecimal.ZERO && item.otherObligationsBD == BigDecimal.ZERO)
                    getCurrencyFormat(item.getPayoffAfterTaxesValue())
                else getCurrencyFormat(item.getPayoffAfterTaxesValueBD())

            taxesTextView.isVisible = item.taxes > 0
            taxesTitleTextView.isVisible = item.taxes > 0
            otherObligationTextView.isVisible = item.otherObligations > 0
            otherObligationTitleTextView.isVisible = item.otherObligations > 0
            payoffAfterTaxesTextView.isVisible = item.taxes > 0
            payoffAfterTaxesTitleTextView.isVisible = item.taxes > 0
        }
    }

    private fun getCurrencyFormat(number: Float): String {
        if (number == 0f) {
            return "0 \u20BD"
        }
        val symbols = DecimalFormatSymbols().apply {
            decimalSeparator = ','
            groupingSeparator = ' '
        }
        return DecimalFormat("#,###.00 \u20BD", symbols).format(number)
    }

    private fun getCurrencyFormat(number: BigDecimal): String {
        if (number == BigDecimal.ZERO) {
            return "0 \u20BD"
        }
        val symbols = DecimalFormatSymbols().apply {
            decimalSeparator = ','
            groupingSeparator = ' '
        }
        return DecimalFormat("#,###.00 \u20BD", symbols).format(number.setScale(2, BigDecimal.ROUND_HALF_UP))
    }
}