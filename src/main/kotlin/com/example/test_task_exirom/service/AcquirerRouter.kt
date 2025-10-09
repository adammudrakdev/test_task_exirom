package com.example.test_task_exirom.service

import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.exception.CardValidationException
import org.springframework.stereotype.Component
import java.util.regex.Pattern

const val REDUCE_CEILING = 9

@Component
class AcquirerRouter(val privatBankPaymentServiceImpl: PaymentService, val otpBankPaymentServiceImpl: PaymentService) {

    fun routeToAcquirer(transactionDto: TransactionDto) {
        LuhnAlgoUtil.validateCardNumberToLuhn(transactionDto.cardNumber)
        val binCardNumber = transactionDto.cardNumber.take(6)
        var sum = 0
        for (digit in 0 ..< binCardNumber.length) {
            sum += (binCardNumber[digit]).digitToInt()
        }
        if (sum / 2 == 0) privatBankPaymentServiceImpl.processPayment(transactionDto)
        else otpBankPaymentServiceImpl.processPayment(transactionDto)
    }
}

//413051 - privat example
//4130 5100 1234 5671
//4130 5198 7654 3209

//406759 - otp example
//4067 5900 6543 2182
//4067 5988 1122 3341