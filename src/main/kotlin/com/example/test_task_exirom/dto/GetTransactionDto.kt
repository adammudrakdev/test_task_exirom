package com.example.test_task_exirom.dto

import com.example.test_task_exirom.enum.Currency
import com.example.test_task_exirom.enum.TransactionStatus

data class GetTransactionDto(val transactionId: Long, val cardNumber: String, val cvv: String, val amount: Double, val currency: Currency,
                             val merchantId: String, val status: TransactionStatus)
