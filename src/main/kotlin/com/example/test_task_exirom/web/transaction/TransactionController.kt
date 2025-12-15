package com.example.test_task_exirom.web.transaction

import com.example.test_task_exirom.component.transaction.services.master.TransactionService
import com.example.test_task_exirom.web.transaction.dto.TransactionResponseDto
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
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
    fun processTransaction(@Valid @RequestBody transactionDto: TransactionDto): TransactionResponseDto {
        return transactionService.processTransaction(transactionDto)
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): TransactionResponseDto {
        return transactionService.getTransactionById(id)
    }

    @GetMapping
    fun getAllTransactions(): List<TransactionResponseDto> {
        return transactionService.getAllTransactions()
    }

    @GetMapping("/merchants/{id}")
    fun getAllTransactionsByMerchantId(@PathVariable id: String): List<TransactionResponseDto> {
        return transactionService.getAllTransactionsByMerchantId(id)
    }

    @GetMapping("/internal/{id}")
    fun getTransactionByIdInternal(@PathVariable id: Long): TransactionResponseDto {
        return transactionService.getTransactionByIdInternal(id)
    }

    @GetMapping("/internal")
    fun getAllTransactionsInternal(): List<TransactionResponseDto> {
        return transactionService.getAllTransactionsInternal()
    }
}