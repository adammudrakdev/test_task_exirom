package com.example.test_task_exirom.infra.db

import java.util.concurrent.atomic.AtomicLong

object IdGenerator {
    private var currentTransactionId = AtomicLong(0)

    fun nextTransactionId(): Long = currentTransactionId.incrementAndGet()

    fun clearIdState() {
        currentTransactionId = AtomicLong(0)
    }
}
