package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.service.utils.LuhnAlgoUtil
import org.springframework.stereotype.Component

const val REDUCE_CEILING = 9

@Component
class AcquirerRouter(val privatBankPaymentServiceImpl: PaymentService, val otpBankPaymentServiceImpl: PaymentService) {

    fun routeToAcquirer(transactionDto: TransactionDto): GetTransactionDto {
        LuhnAlgoUtil.validateCardNumberToLuhn(transactionDto.cardNumber)
        val binCardNumber = transactionDto.cardNumber.take(6)
        var sum = 0
        for (digit in 0 ..< binCardNumber.length) {
            sum += (binCardNumber[digit]).digitToInt()
        }
        return if (sum / 2 == 0) privatBankPaymentServiceImpl.processPayment(transactionDto)
        else otpBankPaymentServiceImpl.processPayment(transactionDto)
    }
}
