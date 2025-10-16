package com.example.test_task_exirom.component.transaction.mapper

import com.example.test_task_exirom.common.Constants.BASE_YEAR
import com.example.test_task_exirom.common.Constants.EXPIRY_DATE_THIRD_CHAR_DELIMITER
import com.example.test_task_exirom.common.Constants.EXPIRY_MONTH_FIRST_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_MONTH_SECOND_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_YEAR_FIRST_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_YEAR_SECOND_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.MONTH_PREFIX_FOR_SINGLE_DIGIT
import com.example.test_task_exirom.common.Constants.OCTOBER_MONTH_NUMBER
import com.example.test_task_exirom.common.Constants.YEAR_STRING_LAST_TWO_DIGITS_INDEX
import java.time.LocalDate
import java.time.YearMonth

object DateMapperUtil {
    fun toLocalDate(expiryDate: String): LocalDate {
        val month: Int =
            if (expiryDate[EXPIRY_MONTH_FIRST_DIGIT_INDEX] == MONTH_PREFIX_FOR_SINGLE_DIGIT) {
                expiryDate[EXPIRY_MONTH_SECOND_DIGIT_INDEX].digitToInt()
            } else {
                ("${expiryDate[EXPIRY_MONTH_FIRST_DIGIT_INDEX]}${expiryDate[EXPIRY_MONTH_SECOND_DIGIT_INDEX]}").toInt()
            }
        val year: Int = BASE_YEAR + ("${expiryDate[EXPIRY_YEAR_FIRST_DIGIT_INDEX]}${expiryDate[EXPIRY_YEAR_SECOND_DIGIT_INDEX]}").toInt()

        return YearMonth.of(year, month).atEndOfMonth()
    }

    fun toExpiryDateDto(expiryDate: LocalDate): String {
        val month = expiryDate.month.ordinal
        val year = expiryDate.year.toString().substring(YEAR_STRING_LAST_TWO_DIGITS_INDEX)

        return StringBuilder().append(if (month < OCTOBER_MONTH_NUMBER) MONTH_PREFIX_FOR_SINGLE_DIGIT.plus(month) else month)
            .append(EXPIRY_DATE_THIRD_CHAR_DELIMITER)
            .append(year).toString()
    }
}