package com.example.test_task_exirom.service

import com.example.test_task_exirom.dto.TransactionDto

interface PaymentService {
    fun processPayment(transactionDto: TransactionDto)
}