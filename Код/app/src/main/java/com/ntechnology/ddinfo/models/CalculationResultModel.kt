package com.ntechnology.ddinfo.models

import java.math.BigDecimal

data class CalculationResultModel(
    val payoff: Float, val taxes: Float = 0f, val otherObligations: Float = 0f) {

    var payoffBD: BigDecimal = BigDecimal.ZERO
    var taxesBD = BigDecimal.ZERO
    var otherObligationsBD = BigDecimal.ZERO

    fun getPayoffAfterTaxesValue(): Float {
        val payoffAfterTaxes = payoff - taxes - otherObligations
        return if (payoffAfterTaxes < .3f * payoff) {
            payoff * .3f
        } else {
            payoffAfterTaxes
        }
    }

    fun getPayoffAfterTaxesValueBD(): BigDecimal {
        val payoffAfterTaxesBD = payoffBD - taxesBD - otherObligationsBD
        return if (payoffAfterTaxesBD < .3f.toBigDecimal() * payoffBD) {
            payoffBD * .3f.toBigDecimal()
        } else {
            payoffAfterTaxesBD
        }
    }
}