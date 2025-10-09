package com.example.test_task_exirom.repository

import com.example.test_task_exirom.entity.Transaction
import com.example.test_task_exirom.exception.NoTransactionException
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class TransactionRepository {
    val database: Map<Long, Transaction> = LinkedHashMap()
    var transactionId = 1L

    fun save(transaction: Transaction) {
        transaction.transactionId = transactionId++
        database.plus(Pair(transaction.transactionId, transaction))
    }

    fun getById(id: Long): Transaction {
        try {
            return database[id]!!
        } catch (_: Exception) {
            throw NoTransactionException("No transaction with id $id was found!")
        }
    }

    fun getAll() : List<Transaction> {
        val transactionList = ArrayList<Transaction>()
        for (key in database.keys) {
            transactionList.plus(database[key])
        }
        return transactionList
    }

    fun getAllByMerchantId(id: Long) : List<Transaction> {
        val transactionList = ArrayList<Transaction>()
        for (key in database.keys) {
            val transaction: Transaction = database[key]!!
            if (transaction.merchantId == id) {
                transactionList.plus(transaction)
            }
        }
        return transactionList
    }
}