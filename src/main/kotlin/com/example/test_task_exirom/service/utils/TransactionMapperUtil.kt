package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.entity.Transaction
import java.util.ArrayList

object TransactionMapperUtil {
    fun mapToEntity(transactionDto: TransactionDto): Transaction {
        return Transaction( cardNumber = transactionDto.cardNumber, cvv = transactionDto.cvv, amount = transactionDto.amount,
            currency = transactionDto.currency, merchantId = transactionDto.merchantId)
    }

    fun mapToDto(transaction: Transaction): GetTransactionDto {
        return GetTransactionDto(transaction.transactionId, transaction.cardNumber, transaction.cvv, transaction.amount,
            transaction.currency, transaction.merchantId, transaction.status)
    }

    fun mapToDtoList(transactionList: List<Transaction>): List<GetTransactionDto> {
        val transactionDtoList: MutableList<GetTransactionDto> = ArrayList<GetTransactionDto>()
        for (transaction in transactionList) {
            transactionDtoList.add(mapToDto(transaction))
        }
        return transactionDtoList
    }
}