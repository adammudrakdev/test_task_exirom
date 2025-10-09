package com.example.test_task_exirom.controller

import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.service.AcquirerRouter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(val acquirerRouter: AcquirerRouter) {
    @PostMapping
    fun processTransaction(transactionDto: TransactionDto) {
        acquirerRouter.routeToAcquirer(transactionDto)
    }

    @GetMapping
    fun getTransactionById(id: Long) {

    }
}