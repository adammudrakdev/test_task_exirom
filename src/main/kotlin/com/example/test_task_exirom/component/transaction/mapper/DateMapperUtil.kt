package com.example.test_task_exirom.component.transaction.mapper

import com.example.test_task_exirom.component.transaction.validation.BASE_YEAR
import java.time.LocalDate
import java.time.YearMonth

const val EXPIRY_DATE_DELIMITER = "/"

object DateMapperUtil {
    fun toLocalDate(expiryDate: String): LocalDate {
        val month: Int = if (expiryDate[0] == '0') expiryDate[1].digitToInt() else ("${expiryDate[0]}${expiryDate[1]}").toInt()
        val year: Int = BASE_YEAR + ("${expiryDate[3]}${expiryDate[4]}").toInt()
        return YearMonth.of(year, month).atEndOfMonth()
    }

    fun toExpiryDateDto(expiryDate: LocalDate): String {
        val month = expiryDate.month.ordinal
        val year = expiryDate.year.toString().substring(2)
        return StringBuilder().append(if (month < 10) "0".plus(month) else month)
            .append(EXPIRY_DATE_DELIMITER)
            .append(year).toString()
    }
}