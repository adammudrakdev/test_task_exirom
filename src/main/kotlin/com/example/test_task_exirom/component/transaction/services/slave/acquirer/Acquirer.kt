package com.example.test_task_exirom.component.transaction.services.slave.acquirer

import com.example.test_task_exirom.component.transaction.model.Transaction

interface Acquirer {
    fun approveTransaction(transaction: Transaction): Transaction
}