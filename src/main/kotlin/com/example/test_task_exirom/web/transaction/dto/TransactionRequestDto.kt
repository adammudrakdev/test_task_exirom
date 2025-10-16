package com.example.test_task_exirom.web.transaction.dto

import com.example.test_task_exirom.common.Constants.CARD_DIGITS
import com.example.test_task_exirom.common.Constants.CARD_SIZE
import com.example.test_task_exirom.common.Constants.CVV_DIGITS_AND_SIZE_REGEX
import com.example.test_task_exirom.common.Constants.MERCHANT_ID_CHARS_AND_SIZE_REGEX
import com.example.test_task_exirom.component.transaction.model.Currency
import com.example.test_task_exirom.component.transaction.validation.ValidExpiryDate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

@JsonIgnoreProperties(ignoreUnknown = true)
data class TransactionDto(
    @field:Schema(name = "cardNumber", example = "0000000000000000", required = true)
    @field:NotBlank(message = "cardNumber: must not be blank")
    @field:Pattern(regexp = CARD_DIGITS, message = "cardNumber: must match pattern 0000000000000000")
    @field:Pattern(regexp = CARD_SIZE, message = "cardNumber: must be 16 digits long")
    val cardNumber: String,

    @field:Schema(name = "expiryDate", example = "01/26", required = true)
    @field:NotBlank(message = "expiryDate: must not be blank")
    @field:ValidExpiryDate
    val expiryDate: String,

    @field:Schema(name = "cvv", example = "000", required = true)
    @field:NotBlank(message = "cvv: must not be blank")
    @field:Pattern(regexp = CVV_DIGITS_AND_SIZE_REGEX, message = "cvv: must match pattern 000")
    val cvv: String,

    @field:Schema(name = "amount", example = "125", required = true)
    @field:Digits(integer = 7, fraction = 2, message = "amount: must match pattern 999999.99")
    val amount: Double,

    @field:Schema(oneOf = [Currency::class], name = "currency", example = "USD", required = true)
    val currency: Currency,

    @field:Schema(name = "merchantId", example = "123456789", required = true)
    @field:NotBlank(message = "merchantId: must not be blank")
    @field:Pattern(regexp = MERCHANT_ID_CHARS_AND_SIZE_REGEX, message = "merchantId: must be 3 chars long, any chars allowed")
    val merchantId: String)
