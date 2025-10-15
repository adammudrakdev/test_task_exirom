package com.example.test_task_exirom.infra.db

import com.example.test_task_exirom.component.transaction.model.Transaction
import java.util.concurrent.atomic.AtomicLong

object DbConnector {
    val database: MutableMap<Long, Transaction> = LinkedHashMap()
    var currentTransactionId = AtomicLong(0)

    val merchantDatabase: List<String> = listOf("1qW", "2eR", "3tY", "4uI", "5oP")
}