package com.example.test_task_exirom.controller

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.service.TransactionService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionController(val transactionService: TransactionService) {
    @PostMapping
    fun processTransaction(@Valid @RequestBody transactionDto: TransactionDto): GetTransactionDto {
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

    @GetMapping("/merchants/{id}")
    fun getAllTransactionsByMerchantId(@PathVariable id: String): List<GetTransactionDto> {
        return transactionService.getAllTransactionsByMerchantId(id)
    }

    @GetMapping("/internal/{id}")
    fun getTransactionByIdInternal(@PathVariable id: Long): GetTransactionDto {
        return transactionService.getTransactionByIdInternal(id)
    }

    @GetMapping("/internal")
    fun getAllTransactionsInternal(): List<GetTransactionDto> {
        return transactionService.getAllTransactionsInternal()
    }
}