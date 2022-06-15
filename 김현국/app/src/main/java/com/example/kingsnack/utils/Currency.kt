package com.example.kingsnack.utils

import java.math.BigDecimal
import java.text.NumberFormat

/**
 * @author 김현국
 * @created 2022/06/14
 */

fun formatPrice(price: Long): String {
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price).movePointLeft(2)
    )
}
