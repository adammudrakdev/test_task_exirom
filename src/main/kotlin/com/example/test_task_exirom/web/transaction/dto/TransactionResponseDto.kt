package com.example.test_task_exirom.web.transaction.dto

import com.example.test_task_exirom.component.transaction.model.Currency
import com.example.test_task_exirom.component.transaction.model.TransactionStatus

data class TransactionResponseDto(val transactionId: Long, val cardNumber: String, val expiryDate: String, val cvv: String, val amount: Double, val currency: Currency,
                                  val merchantId: String, val status: TransactionStatus)
