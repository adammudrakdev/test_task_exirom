package com.example.test_task_exirom.component.transaction.services.slave.acquirer

import com.example.test_task_exirom.common.Utils.isOdd
import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.model.TransactionStatus
import com.example.test_task_exirom.component.transaction.exception.TransactionDeniedException
import org.springframework.stereotype.Service

@Service
class AcquirerA : Acquirer {
    override fun approveTransaction(transaction: Transaction): Transaction {
        return try {
            verifyTransaction(transaction)
            transaction.status = TransactionStatus.APPROVED
            transaction
        } catch (_: TransactionDeniedException) {
            transaction.status = TransactionStatus.DENIED
            transaction
        }
    }

    fun verifyTransaction(transaction: Transaction) {
        val isCardNumberLastDigitOdd = transaction.cardNumber.last().digitToInt().isOdd()
        if (isCardNumberLastDigitOdd) {
            throw TransactionDeniedException("Transaction was denied. Contact your acquirer")
        }
    }
}