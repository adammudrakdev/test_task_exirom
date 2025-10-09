package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.entity.Transaction
import com.example.test_task_exirom.enum.TransactionStatus
import com.example.test_task_exirom.exception.TransactionDeniedException
import com.example.test_task_exirom.repository.TransactionRepository
import com.example.test_task_exirom.service.utils.TransactionMapperUtil
import com.example.test_task_exirom.service.utils.TransactionProcessingUtil
import org.springframework.stereotype.Service

@Service
class PrivatBankPaymentServiceImpl(val transactionRepository: TransactionRepository): PaymentService {
    override fun processPayment(transactionDto: TransactionDto): GetTransactionDto {
        val transaction: Transaction = TransactionMapperUtil.mapToEntity(transactionDto)
        transactionRepository.save(transaction)
        val transactionFromRepo = transactionRepository.getById(transactionRepository.transactionId - 1)
        println(transactionFromRepo)
        Thread.sleep(5000)
        try {
            TransactionProcessingUtil.verifyTransaction(transaction)
            transactionFromRepo.status = TransactionStatus.APPROVED
        } catch (_: TransactionDeniedException) {
            transactionFromRepo.status = TransactionStatus.DENIED
        }
        return TransactionMapperUtil.mapToDto(transactionFromRepo)
    }
}