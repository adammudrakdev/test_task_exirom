package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.exception.NotValidAmountException

const val MIN = 0.1
const val MAX = 1000000.0

object RangeChecker {
    fun checkRange(amount: Double) {
        if (amount !in MIN..MAX) {
            throw NotValidAmountException("Amount must be inclusively from $MIN to $MAX")
        }
    }
}