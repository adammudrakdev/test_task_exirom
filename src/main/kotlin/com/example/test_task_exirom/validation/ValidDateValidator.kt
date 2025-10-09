package com.example.test_task_exirom.validation

import com.example.test_task_exirom.service.utils.DateMapperUtil
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.util.regex.Pattern

const val BASE_YEAR = 2000
const val DATE_REGEX = "^(0[1-9]|1[0-2])/[0-9]{2}$"

class ValidDateValidator : ConstraintValidator<ValidDate, String> {

    override fun isValid(expiryDate: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern.matches(DATE_REGEX, expiryDate) && LocalDate.now().isBefore(DateMapperUtil.toLocalDate(expiryDate))
    }
}