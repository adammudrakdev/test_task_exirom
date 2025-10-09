package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.entity.Transaction
import java.util.ArrayList

object TransactionMapperUtil {
    fun mapToEntity(transactionDto: TransactionDto): Transaction {
        return Transaction( cardNumber = transactionDto.cardNumber, expiryDate = DateMapperUtil.toLocalDate(transactionDto.expiryDate),
            cvv = transactionDto.cvv, amount = transactionDto.amount, currency = transactionDto.currency, merchantId = transactionDto.merchantId)
    }

    fun mapToDto(transaction: Transaction): GetTransactionDto {
        return GetTransactionDto(transaction.transactionId, MaskerUtils.maskCreditCard(transaction.cardNumber),
            MaskerUtils.maskExpiryDate(DateMapperUtil.toExpiryDateDto(transaction.expiryDate)),
            MaskerUtils.maskCvv(transaction.cvv), transaction.amount, transaction.currency, transaction.merchantId, transaction.status)
    }

    fun mapToDtoInternal(transaction: Transaction): GetTransactionDto {
        return GetTransactionDto(
            transaction.transactionId, transaction.cardNumber,
            DateMapperUtil.toExpiryDateDto(transaction.expiryDate),
            transaction.cvv, transaction.amount, transaction.currency, transaction.merchantId, transaction.status)
    }

    fun mapToDtoList(transactionList: List<Transaction>): List<GetTransactionDto> {
        val transactionDtoList: MutableList<GetTransactionDto> = ArrayList<GetTransactionDto>()
        for (transaction in transactionList) {
            transactionDtoList.add(mapToDto(transaction))
        }
        return transactionDtoList
    }

    fun mapToDtoListInternal(transactionList: List<Transaction>): List<GetTransactionDto> {
        val transactionDtoList: MutableList<GetTransactionDto> = ArrayList<GetTransactionDto>()
        for (transaction in transactionList) {
            transactionDtoList.add(mapToDtoInternal(transaction))
        }
        return transactionDtoList
    }
}