package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto

const val RED = "\u001B[31m"
const val RESET = "\u001B[0m"


interface PaymentService {
    fun processPayment(transactionDto: TransactionDto): GetTransactionDto
}