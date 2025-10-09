package com.example.test_task_exirom.dto

import com.example.test_task_exirom.enum.Currency
import com.example.test_task_exirom.validation.ValidDate
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

const val CARD_DIGITS_AND_SIZE_REGEX = "\\d{16}"
const val CVV_DIGITS_AND_SIZE_REGEX = "\\d{3}"
const val DATE_REGEX = "^(0[1-9]|1[0-2])/[0-9]{2}$\n"

data class TransactionDto(
    @field:Schema(name = "cardNumber", example = "0000000000000000", required = true)
    @field:NotBlank
    @field:Pattern(regexp = CARD_DIGITS_AND_SIZE_REGEX)
    val cardNumber: String,

    @field:Schema(name = "expiryDate", example = "01/26", required = true)
    @field:NotBlank
    @field:Pattern(regexp = DATE_REGEX)
    @field:ValidDate
    val expiryDate: String,

    @field:Schema(name = "cvv", example = "000", required = true)
    @field:NotBlank
    @field:Pattern(regexp = CVV_DIGITS_AND_SIZE_REGEX)
    val cvv: String,

    @field:Schema(name = "amount", example = "0.1", required = true)
    @field:NotNull
    @field:Digits(integer = 7, fraction = 2)
    @field:Max(1000000)
    val amount: Double,

    @field:Schema(oneOf = [Currency::class],name = "currency", example = "USD", required = true)
    @field:NotNull
    val currency: Currency,

    @field:Schema(name = "merchantId", example = "123456789", required = true)
    @field:NotNull
    @field:Digits(integer = 17, fraction = 0)
    val merchantId: Long)
