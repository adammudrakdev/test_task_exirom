package com.example.test_task_exirom.service.utils

object MaskerUtils {
    fun maskCreditCard(cardNumber: String): String {
        return cardNumber.replaceRange(6, 12, "*".repeat(6))
    }

    fun maskCvv(cvv: String): String {
        return cvv.replaceRange(0,3, "*".repeat(3))
    }

    fun maskExpiryDate(expiryDate: String): String {
        return expiryDate.replaceRange(0,2, "*".repeat(2)).replaceRange(3, 5, "*".repeat(2))
    }
}