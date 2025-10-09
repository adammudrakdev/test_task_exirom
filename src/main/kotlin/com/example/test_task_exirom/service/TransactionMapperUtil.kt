package com.example.test_task_exirom.service

import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.entity.Transaction

object TransactionMapperUtil {
    fun map(transactionDto: TransactionDto): Transaction {
        return Transaction( cardNumber = transactionDto.cardNumber, cvv = transactionDto.cvv, amount = transactionDto.amount,
            currency = transactionDto.currency, merchantId = transactionDto.merchantId)
    }
}