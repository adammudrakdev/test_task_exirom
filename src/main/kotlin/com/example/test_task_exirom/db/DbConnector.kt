package com.example.test_task_exirom.db

import com.example.test_task_exirom.entity.Transaction

object DbConnector {
    val database: MutableMap<Long, Transaction> = LinkedHashMap()
    var nextTransactionId = 1L
}