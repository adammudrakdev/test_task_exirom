package com.example.test_task_exirom.component.transaction.validation

import com.example.test_task_exirom.common.Constants.DATE_REGEX
import com.example.test_task_exirom.component.transaction.mapper.DateMapperUtil
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.util.regex.Pattern

class ValidDateValidator : ConstraintValidator<ValidExpiryDate, String> {

    override fun isValid(expiryDate: String, context: ConstraintValidatorContext?): Boolean {
        return Pattern.matches(DATE_REGEX, expiryDate) && LocalDate.now().isBefore(DateMapperUtil.toLocalDate(expiryDate))
    }
}