package com.example.test_task_exirom.common

import com.example.test_task_exirom.common.Constants.REDUCE_CEILING

object Utils {
    fun Int.isOdd(): Boolean {
        return this % 2 != 0
    }

    fun Int.isEven(): Boolean {
        return this % 2 == 0
    }

    fun Int.isMultipleOfTen(): Boolean {
        return this % 10 == 0
    }

    fun Int.reduceToCeiling(): Int {
        return if (this > REDUCE_CEILING) {
            this - 9
        } else {
            this
        }
    }
}