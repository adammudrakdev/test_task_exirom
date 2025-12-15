package com.example.test_task_exirom.component.transaction.services.master

import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.model.TransactionRepository
import com.example.test_task_exirom.component.transaction.services.slave.AcquirerRouter
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.Acquirer
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.AcquirerA
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.AcquirerB
import com.example.test_task_exirom.component.transaction.validation.TransactionValidator
import com.example.test_task_exirom.web.transaction.dto.TransactionResponseDto
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import com.example.test_task_exirom.web.transaction.dto.mapToDto
import com.example.test_task_exirom.web.transaction.dto.mapToDtoInternal
import com.example.test_task_exirom.web.transaction.dto.mapToDtoList
import com.example.test_task_exirom.web.transaction.dto.mapToDtoListInternal
import com.example.test_task_exirom.web.transaction.dto.mapToEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(val acquirerRouter: AcquirerRouter,
                         val transactionValidator: TransactionValidator,
                         val transactionRepository: TransactionRepository) {
    private val logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)

    fun processTransaction(transactionDto: TransactionDto): TransactionResponseDto {
        transactionValidator.validateTransactionDto(transactionDto)
        val transaction: Transaction = transactionDto.mapToEntity()
        val acquirer: Acquirer = acquirerRouter.routeToAcquirer(transaction)
        logger.info(getAcquirerType(acquirer))
        val processedTransaction = acquirer.approveTransaction(transaction)

        return transactionRepository.save(processedTransaction).mapToDto()
    }

    fun getTransactionById(id: Long): TransactionResponseDto {
        return transactionRepository.getById(id).mapToDto()
    }

    fun getAllTransactions(): List<TransactionResponseDto> {
        return transactionRepository.getAll().mapToDtoList()
    }

    fun getAllTransactionsByMerchantId(id: String): List<TransactionResponseDto> {
        return transactionRepository.getAllByMerchantId(id).mapToDtoList()
    }

    fun getTransactionByIdInternal(id: Long): TransactionResponseDto {
        return transactionRepository.getById(id).mapToDtoInternal()
    }

    fun getAllTransactionsInternal(): List<TransactionResponseDto> {
        return transactionRepository.getAll().mapToDtoListInternal()
    }

    private fun getAcquirerType(acquirer: Acquirer): String {
        return when (acquirer) {
            is AcquirerA -> "AcquirerA is working..."
            is AcquirerB -> "AcquirerB is working..."
            else -> throw IllegalArgumentException("No such acquirer specified in the system: ${acquirer.javaClass.simpleName}")
        }
    }
}