package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.db.DbConnector
import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.entity.Transaction
import com.example.test_task_exirom.enum.TransactionStatus
import com.example.test_task_exirom.exception.TransactionDeniedException
import com.example.test_task_exirom.repository.TransactionRepository
import com.example.test_task_exirom.service.utils.TransactionMapperUtil
import com.example.test_task_exirom.service.utils.TransactionProcessingUtil

abstract class CommonPaymentService(val transactionRepository: TransactionRepository, val acquirer: String): PaymentService {
    override fun processPayment(transactionDto: TransactionDto): GetTransactionDto {
        println(PURPLE + "Using $acquirer Acquirer" + RESET)
        val transaction: Transaction = TransactionMapperUtil.mapToEntity(transactionDto)
        transactionRepository.save(transaction)
        val transactionFromRepo = transactionRepository.getById(DbConnector.currentTransactionId.get())
        for (i in 1..5) {
            println(BLUE + transactionFromRepo.status.name + RESET)
            Thread.sleep(1000)
        }
        try {
            TransactionProcessingUtil.verifyTransaction(transaction)
            transactionFromRepo.status = TransactionStatus.APPROVED
            transactionRepository.save(transactionFromRepo)
            println(GREEN + transactionFromRepo.status.name + RESET)
        } catch (_: TransactionDeniedException) {
            transactionFromRepo.status = TransactionStatus.DENIED
            transactionRepository.save(transactionFromRepo)
            println(RED + transactionFromRepo.status.name + RESET)
        }
        return TransactionMapperUtil.mapToDto(transactionFromRepo)
    }
}