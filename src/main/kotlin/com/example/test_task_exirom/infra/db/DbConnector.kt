package com.example.test_task_exirom.infra.db

import com.example.test_task_exirom.component.transaction.model.Transaction

object DbConnector {
    val database: MutableMap<Long, Transaction> = LinkedHashMap()

    val merchantDatabase: List<String> = listOf("1qW", "2eR", "3tY", "4uI", "5oP")
}