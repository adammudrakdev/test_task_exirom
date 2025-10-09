package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.exception.CardValidationException

const val REDUCE_CEILING = 9

object LuhnAlgoUtil {
    fun validateCardNumberToLuhn(cardNumber: String) {
        val digits = cardNumber.reversed().split("")
        var sum = 0
        for (i in 0..< digits.size) {
            if (digits[i] == "") continue
            if (i % 2 == 0) {
                val doubledCurrent = digits[i].toInt() * 2
                sum += if (doubledCurrent > REDUCE_CEILING) {
                    doubledCurrent - 9
                } else {
                    doubledCurrent
                }
            } else {
                sum += digits[i].toInt()
            }
        }
        if (sum % 10 != 0) {
            throw CardValidationException("Invalid card number! Please try again...")
        }
    }
}