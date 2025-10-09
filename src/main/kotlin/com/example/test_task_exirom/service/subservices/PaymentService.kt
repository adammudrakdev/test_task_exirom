package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto

interface PaymentService {
    fun processPayment(transactionDto: TransactionDto): GetTransactionDto
}