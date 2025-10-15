package com.example.test_task_exirom.component.transaction.validation

import com.example.test_task_exirom.infra.db.DbConnector
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import com.example.test_task_exirom.component.transaction.exception.CardValidationException
import com.example.test_task_exirom.component.transaction.exception.MerchantNotFoundException
import com.example.test_task_exirom.component.transaction.exception.NotValidAmountException
import org.springframework.stereotype.Component

const val MIN = 0.1
const val MAX = 1000000.0
const val REDUCE_CEILING = 9

@Component
class TransactionValidator {
    fun validateTransactionDto(transactionDto: TransactionDto) {
        validateCardNumberToLuhn(transactionDto.cardNumber)
        validateRange(transactionDto.amount)
        validateMerchant(transactionDto.merchantId)
    }

    private fun validateCardNumberToLuhn(cardNumber: String) {
        val digits = cardNumber.reversed().split("")
        var sum = 0
        for (i in 0..< digits.size) {
            if (digits[i] == "") continue
            if (i % 2 == 0) {
                val doubledCurrent = digits[i].toInt() * 2
                sum += if (doubledCurrent > REDUCE_CEILING) {
                    doubledCurrent - 9
                } else {
                    doubledCurrent
                }
            } else {
                sum += digits[i].toInt()
            }
        }
        if (sum % 10 != 0) {
            throw CardValidationException("Invalid card number! Please try again...")
        }
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