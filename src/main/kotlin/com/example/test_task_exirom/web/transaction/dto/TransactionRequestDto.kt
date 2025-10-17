package com.example.test_task_exirom.web.transaction.dto

import com.example.test_task_exirom.common.Constants.BASE_YEAR
import com.example.test_task_exirom.common.Constants.BIN_END_INDEX
import com.example.test_task_exirom.common.Constants.CARD_DIGITS
import com.example.test_task_exirom.common.Constants.CARD_SIZE
import com.example.test_task_exirom.common.Constants.CVV_DIGITS_AND_SIZE_REGEX
import com.example.test_task_exirom.common.Constants.EXPIRY_DATE_THIRD_CHAR_DELIMITER
import com.example.test_task_exirom.common.Constants.EXPIRY_MONTH_FIRST_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_MONTH_SECOND_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_YEAR_FIRST_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.EXPIRY_YEAR_SECOND_DIGIT_INDEX
import com.example.test_task_exirom.common.Constants.LAST_FOUR_START_INDEX
import com.example.test_task_exirom.common.Constants.MASKED_CARD_MIDDLE_LENGTH
import com.example.test_task_exirom.common.Constants.MASKED_CVV
import com.example.test_task_exirom.common.Constants.MASKED_EXPIRY_DATE
import com.example.test_task_exirom.common.Constants.MASKER_CHAR
import com.example.test_task_exirom.common.Constants.MERCHANT_ID_CHARS_AND_SIZE_REGEX
import com.example.test_task_exirom.common.Constants.MONTH_PREFIX_FOR_SINGLE_DIGIT
import com.example.test_task_exirom.common.Constants.OCTOBER_MONTH_NUMBER
import com.example.test_task_exirom.common.Constants.YEAR_STRING_LAST_TWO_DIGITS_INDEX
import com.example.test_task_exirom.component.transaction.model.Currency
import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.validation.ValidExpiryDate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.YearMonth
import java.util.ArrayList

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
    val merchantId: String) {

    fun TransactionDto.mapToEntity(): Transaction {
        return Transaction(
            cardNumber = this.cardNumber,
            expiryDate = toLocalDate(this.expiryDate),
            cvv = this.cvv,
            amount = this.amount,
            currency = this.currency,
            merchantId = this.merchantId)
    }

    fun Transaction.mapToDto(): TransactionResponseDto {
        return TransactionResponseDto(
            this.transactionId,
            maskCreditCard(this.cardNumber),
            MASKED_EXPIRY_DATE,
            MASKED_CVV,
            this.amount,
            this.currency,
            this.merchantId,
            this.status)
    }

    fun Transaction.mapToDtoInternal(): TransactionResponseDto {
        return TransactionResponseDto(
            this.transactionId,
            this.cardNumber,
            toExpiryDateDto(this.expiryDate),
            this.cvv,
            this.amount,
            this.currency,
            this.merchantId,
            this.status)
    }

    fun List<Transaction>.mapToDtoList(): List<TransactionResponseDto> {
        val transactionDtoList: MutableList<TransactionResponseDto> = ArrayList<TransactionResponseDto>()
        for (transaction in this) {
            transactionDtoList.add(transaction.mapToDto())
        }

        return transactionDtoList
    }

    fun List<Transaction>.mapToDtoListInternal(): List<TransactionResponseDto> {
        val transactionDtoList: MutableList<TransactionResponseDto> = ArrayList<TransactionResponseDto>()
        for (transaction in this) {
            transactionDtoList.add(transaction.mapToDtoInternal())
        }

        return transactionDtoList
    }

    private fun maskCreditCard(cardNumber: String): String {
        return cardNumber.replaceRange(BIN_END_INDEX, LAST_FOUR_START_INDEX,MASKER_CHAR.repeat(MASKED_CARD_MIDDLE_LENGTH))
    }

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
