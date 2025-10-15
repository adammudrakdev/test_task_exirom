package com.example.test_task_exirom.component.transaction.mapper

import com.example.test_task_exirom.web.transaction.dto.GetTransactionDto
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import com.example.test_task_exirom.component.transaction.model.Transaction
import java.util.ArrayList

object TransactionMapperUtil {
    fun mapToEntity(transactionDto: TransactionDto): Transaction {
        return Transaction( cardNumber = transactionDto.cardNumber, expiryDate = DateMapperUtil.toLocalDate(transactionDto.expiryDate),
            cvv = transactionDto.cvv, amount = transactionDto.amount, currency = transactionDto.currency, merchantId = transactionDto.merchantId)
    }

    fun mapToDto(transaction: Transaction): GetTransactionDto {
        return GetTransactionDto(transaction.transactionId, maskCreditCard(transaction.cardNumber),
            maskExpiryDate(DateMapperUtil.toExpiryDateDto(transaction.expiryDate)),
            maskCvv(transaction.cvv), transaction.amount, transaction.currency, transaction.merchantId, transaction.status)
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

    private fun maskCreditCard(cardNumber: String): String {
        return cardNumber.replaceRange(6, 12, "*".repeat(6))
    }

    private fun maskCvv(cvv: String): String {
        return cvv.replaceRange(0,3, "*".repeat(3))
    }

    private fun maskExpiryDate(expiryDate: String): String {
        return expiryDate.replaceRange(0,2, "*".repeat(2)).replaceRange(3, 5, "*".repeat(2))
    }
}