package com.example.test_task_exirom.service

import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.exception.TransactionDeniedException

object TransactionProcessingUtil {
    fun verifyTransaction(transactionDto: TransactionDto) {
        if (transactionDto.cardNumber.last().digitToInt() % 2 != 0)
            throw TransactionDeniedException("Transaction was denied. Contact your acquirer")
    }
}