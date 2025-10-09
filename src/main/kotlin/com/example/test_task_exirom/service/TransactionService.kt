package com.example.test_task_exirom.service

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.repository.TransactionRepository
import com.example.test_task_exirom.service.subservices.AcquirerRouter
import com.example.test_task_exirom.service.utils.TransactionMapperUtil
import org.springframework.stereotype.Service

@Service
class TransactionService(val transactionRepository: TransactionRepository, val acquirerRouter: AcquirerRouter) {
    fun routeTransaction(transactionDto: TransactionDto): GetTransactionDto {
        return acquirerRouter.routeToAcquirer(transactionDto)
    }

    fun getTransactionById(id: Long): GetTransactionDto {
        return TransactionMapperUtil.mapToDto(transactionRepository.getById(id))
    }

    fun getAllTransactions(): List<GetTransactionDto> {
        return TransactionMapperUtil.mapToDtoList(transactionRepository.getAll())
    }

    fun getAllTransactionsByMerchantId(id: Long): List<GetTransactionDto> {
        return TransactionMapperUtil.mapToDtoList(transactionRepository.getAllByMerchantId(id))
    }
}