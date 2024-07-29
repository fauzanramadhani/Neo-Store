package com.ndc.neostore.utils

import java.text.NumberFormat
import java.util.Locale

fun Long.toCurrency(locale: Locale = Locale("id", "ID")): String {
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    return numberFormat.format(this)
}

fun String.isNumber(): Boolean {
    val regex = "^[0-9]+\$".toRegex()
    return this.matches(regex)
}