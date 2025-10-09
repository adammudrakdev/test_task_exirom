package com.example.test_task_exirom.controller

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.service.AcquirerRouter
import com.example.test_task_exirom.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(val transactionService: TransactionService) {
    @PostMapping
    fun processTransaction(transactionDto: TransactionDto): GetTransactionDto {
        return transactionService.routeTransaction(transactionDto)
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): GetTransactionDto {
        return transactionService.getTransactionById(id)
    }

    @GetMapping
    fun getAllTransactions(): List<GetTransactionDto> {
        return transactionService.getAllTransactions()
    }
}