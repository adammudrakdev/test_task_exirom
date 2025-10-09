package com.example.test_task_exirom.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.time.YearMonth

const val BASE_YEAR = 2000

class ValidDateValidator : ConstraintValidator<ValidDate, String> {

    override fun isValid(expiryDate: String, context: ConstraintValidatorContext?): Boolean {
        return LocalDate.now().isBefore(parseExpiryDate(expiryDate))
    }

    private fun parseExpiryDate(expiryDate: String): LocalDate {
        val month: Int = if (expiryDate[0] == '0') expiryDate[1].digitToInt() else ("${expiryDate[0]}${expiryDate[1]}").toInt()
        val year: Int = BASE_YEAR + ("${expiryDate[3]}${expiryDate[4]}").toInt()
        return YearMonth.of(year, month).atEndOfMonth()
    }
}