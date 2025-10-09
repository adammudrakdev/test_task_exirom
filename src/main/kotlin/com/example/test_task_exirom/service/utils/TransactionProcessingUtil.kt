package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.entity.Transaction
import com.example.test_task_exirom.exception.TransactionDeniedException

object TransactionProcessingUtil {
    fun verifyTransaction(transaction: Transaction) {
        if (transaction.cardNumber.last().digitToInt() % 2 != 0) {
            throw TransactionDeniedException("Transaction was denied. Contact your acquirer")
        }
    }
}