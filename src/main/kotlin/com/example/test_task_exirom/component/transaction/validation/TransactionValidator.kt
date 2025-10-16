package com.example.test_task_exirom.component.transaction.validation

import com.example.test_task_exirom.common.Constants.MAX
import com.example.test_task_exirom.common.Constants.MIN
import com.example.test_task_exirom.common.Utils.isMultipleOfTen
import com.example.test_task_exirom.common.Utils.isOdd
import com.example.test_task_exirom.common.Utils.reduceToCeiling
import com.example.test_task_exirom.infra.db.DbConnector
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import com.example.test_task_exirom.component.transaction.exception.CardValidationException
import com.example.test_task_exirom.component.transaction.exception.MerchantNotFoundException
import com.example.test_task_exirom.component.transaction.exception.NotValidAmountException
import org.springframework.stereotype.Component

@Component
class TransactionValidator {
    fun validateTransactionDto(transactionDto: TransactionDto) {
        validateCardNumberToLuhn(transactionDto.cardNumber)
        validateRange(transactionDto.amount)
        validateMerchant(transactionDto.merchantId)
    }

    private fun validateCardNumberToLuhn(cardNumber: String) {
        val digits: List<Int> = getDigitsFromCard(cardNumber)
        val sum: Int = getSumFromDigits(digits)
        if (!sum.isMultipleOfTen()) {
            throw CardValidationException("Invalid card number! Please try again...")
        }
    }

    private fun getDigitsFromCard(cardNumber: String): List<Int>  {
        return cardNumber.reversed().map { it.digitToInt()}
    }

    private fun getSumFromDigits(digits: List<Int>): Int {
        var sum = 0
        for (i in 0..< digits.size) {
            if (i.isOdd()) {
                val doubledCurrent = digits[i] * 2
                sum += doubledCurrent.reduceToCeiling()
            } else {
                sum += digits[i]
            }
        }

        return sum
    }

    private fun validateRange(amount: Double) {
        if (amount !in MIN..MAX) {
            throw NotValidAmountException("Amount must be inclusively from $MIN to $MAX")
        }
    }

    private fun validateMerchant(merchantId: String) {
        if (!DbConnector.merchantDatabase.contains(merchantId)) {
            throw MerchantNotFoundException("Can't find merchant with id $merchantId")
        }
    }
}