package com.example.test_task_exirom.component.transaction.services.master

import com.example.test_task_exirom.component.transaction.mapper.TransactionMapperUtil
import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.model.TransactionRepository
import com.example.test_task_exirom.component.transaction.services.slave.AcquirerRouter
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.Acquirer
import com.example.test_task_exirom.component.transaction.validation.TransactionValidator
import com.example.test_task_exirom.web.transaction.dto.GetTransactionDto
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(val acquirerRouter: AcquirerRouter,
                         val transactionValidator: TransactionValidator,
                         val transactionRepository: TransactionRepository) {
    private val logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)

    fun processTransaction(transactionDto: TransactionDto): GetTransactionDto {
        transactionValidator.validateTransactionDto(transactionDto)
        val transaction: Transaction = TransactionMapperUtil.mapToEntity(transactionDto)
        val acquirer: Acquirer = acquirerRouter.routeToAcquirer(transaction)
        logger.info(getAcquirerType(acquirer))
        val processedTransaction = acquirer.approveTransaction(transaction)

        return TransactionMapperUtil.mapToDto(transactionRepository.save(processedTransaction))
    }

    fun getTransactionById(id: Long): GetTransactionDto {
        return TransactionMapperUtil.mapToDto(transactionRepository.getById(id))
    }

    fun getAllTransactions(): List<GetTransactionDto> {
        return TransactionMapperUtil.mapToDtoList(transactionRepository.getAll())
    }

    fun getAllTransactionsByMerchantId(id: String): List<GetTransactionDto> {
        return TransactionMapperUtil.mapToDtoList(transactionRepository.getAllByMerchantId(id))
    }

    fun getTransactionByIdInternal(id: Long): GetTransactionDto {
        return TransactionMapperUtil.mapToDtoInternal(transactionRepository.getById(id))
    }

    fun getAllTransactionsInternal(): List<GetTransactionDto> {
        return TransactionMapperUtil.mapToDtoListInternal(transactionRepository.getAll())
    }

    private fun getAcquirerType(acquirer: Acquirer): String {
        return when (val name = acquirer::class.simpleName) {
            "AcquirerA" -> "AcquirerA is working..."
            "AcquirerB" -> "AcquirerB is working..."
            else -> throw IllegalArgumentException("No such acquirer specified in the system: $name")
        }
    }
}