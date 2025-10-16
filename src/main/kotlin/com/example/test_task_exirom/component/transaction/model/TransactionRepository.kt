package com.example.test_task_exirom.component.transaction.model

import com.example.test_task_exirom.component.transaction.exception.TransactionNotFoundException
import com.example.test_task_exirom.infra.db.DbConnector
import com.example.test_task_exirom.infra.db.IdGenerator
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class TransactionRepository {

    fun save(transaction: Transaction): Transaction {
        if (transaction.transactionId == 0L) {
            transaction.transactionId = IdGenerator.nextTransactionId()
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