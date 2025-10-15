package com.example.test_task_exirom.repository

import com.example.test_task_exirom.db.DbConnector
import com.example.test_task_exirom.entity.Transaction
import com.example.test_task_exirom.exception.TransactionNotFoundException
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class TransactionRepository {

    fun save(transaction: Transaction): Transaction {
        if (transaction.transactionId == 0L) {
            transaction.transactionId = DbConnector.currentTransactionId.incrementAndGet()
        }
        DbConnector.database[transaction.transactionId] = (transaction)
        return transaction
    }

    fun getById(id: Long): Transaction {
        try {
            return DbConnector.database[id]!!
        } catch (_: Exception) {
            throw TransactionNotFoundException("No transaction with id $id was found!")
        }
    }

    fun getAll() : List<Transaction> {
        return DbConnector.database.values.toList()
    }

    fun getAllByMerchantId(id: String) : List<Transaction> {
        return DbConnector.database.values.filter { t -> id == t.merchantId }.toList()
    }
}