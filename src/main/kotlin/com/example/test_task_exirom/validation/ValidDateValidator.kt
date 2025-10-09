package com.example.test_task_exirom.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.time.YearMonth
import java.util.regex.Pattern

const val BASE_YEAR = 2000
const val DATE_REGEX = "^(0[1-9]|1[0-2])/[0-9]{2}$"

class ValidDateValidator : ConstraintValidator<ValidDate, String> {

    override fun isValid(expiryDate: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern.matches(DATE_REGEX, expiryDate) && LocalDate.now().isBefore(parseExpiryDate(expiryDate))
    }

    private fun parseExpiryDate(expiryDate: String): LocalDate {
        val month: Int = if (expiryDate[0] == '0') expiryDate[1].digitToInt() else ("${expiryDate[0]}${expiryDate[1]}").toInt()
        val year: Int = BASE_YEAR + ("${expiryDate[3]}${expiryDate[4]}").toInt()
        return YearMonth.of(year, month).atEndOfMonth()
    }
}