package com.example.test_task_exirom.component.transaction.mapper

import com.example.test_task_exirom.common.Constants.BIN_END_INDEX
import com.example.test_task_exirom.common.Constants.LAST_FOUR_START_INDEX
import com.example.test_task_exirom.common.Constants.MASKED_CARD_MIDDLE_LENGTH
import com.example.test_task_exirom.common.Constants.MASKED_CVV
import com.example.test_task_exirom.common.Constants.MASKED_EXPIRY_DATE
import com.example.test_task_exirom.common.Constants.MASKER_CHAR
import com.example.test_task_exirom.web.transaction.dto.TransactionResponseDto
import com.example.test_task_exirom.web.transaction.dto.TransactionDto
import com.example.test_task_exirom.component.transaction.model.Transaction
import java.util.ArrayList

object TransactionMapperUtil {
    fun TransactionDto.mapToEntity(): Transaction {
        return Transaction(
            cardNumber = this.cardNumber,
            expiryDate = DateMapperUtil.toLocalDate(this.expiryDate),
            cvv = this.cvv,
            amount = this.amount,
            currency = this.currency,
            merchantId = this.merchantId)
    }

    fun Transaction.mapToDto(): TransactionResponseDto {
        return TransactionResponseDto(
            this.transactionId,
            maskCreditCard(this.cardNumber),
            MASKED_EXPIRY_DATE,
            MASKED_CVV,
            this.amount,
            this.currency,
            this.merchantId,
            this.status)
    }

    fun Transaction.mapToDtoInternal(): TransactionResponseDto {
        return TransactionResponseDto(
            this.transactionId,
            this.cardNumber,
            DateMapperUtil.toExpiryDateDto(this.expiryDate),
            this.cvv,
            this.amount,
            this.currency,
            this.merchantId,
            this.status)
    }

    fun List<Transaction>.mapToDtoList(): List<TransactionResponseDto> {
        val transactionDtoList: MutableList<TransactionResponseDto> = ArrayList<TransactionResponseDto>()
        for (transaction in this) {
            transactionDtoList.add(transaction.mapToDto())
        }

        return transactionDtoList
    }

    fun List<Transaction>.mapToDtoListInternal(): List<TransactionResponseDto> {
        val transactionDtoList: MutableList<TransactionResponseDto> = ArrayList<TransactionResponseDto>()
        for (transaction in this) {
            transactionDtoList.add(transaction.mapToDtoInternal())
        }

        return transactionDtoList
    }

    private fun maskCreditCard(cardNumber: String): String {
        return cardNumber.replaceRange(BIN_END_INDEX, LAST_FOUR_START_INDEX,MASKER_CHAR.repeat(MASKED_CARD_MIDDLE_LENGTH))
    }
}