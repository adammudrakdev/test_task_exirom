package com.example.test_task_exirom.component.transaction.model

import java.time.LocalDate

class Transaction(var transactionId: Long = 0L, val cardNumber: String, val expiryDate: LocalDate, val cvv: String, val amount: Double, val currency: Currency,
                  val merchantId: String, var status: TransactionStatus = TransactionStatus.PENDING) {

    override fun toString(): String {
        return "Transaction(transactionId=$transactionId, cardNumber='$cardNumber', cvv='$cvv', amount=$amount, " +
                "currency=$currency, merchantId='$merchantId', status=$status)"
    }
}