package com.example.test_task_exirom.component.transaction.model

import com.example.test_task_exirom.infra.db.IdGenerator
import java.time.LocalDate

data class Transaction(var transactionId: Long = IdGenerator.nextTransactionId(),
                       val cardNumber: String, val expiryDate: LocalDate,
                       val cvv: String,
                       val amount: Double,
                       val currency: Currency,
                       val merchantId: String,
                       var status: TransactionStatus = TransactionStatus.PENDING)