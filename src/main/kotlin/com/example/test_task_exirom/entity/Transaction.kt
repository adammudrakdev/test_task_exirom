package com.example.test_task_exirom.entity

import com.example.test_task_exirom.enum.Currency
import com.example.test_task_exirom.enum.TransactionStatus

class Transaction(val transactionId: String, val cardNumber: String, val cvv: String, val amount: Double, val currency: Currency,
                  val merchantId: String, var status: TransactionStatus = TransactionStatus.PENDING) {
    fun changeStatus(status: TransactionStatus) {
        this.status = status
    }
}