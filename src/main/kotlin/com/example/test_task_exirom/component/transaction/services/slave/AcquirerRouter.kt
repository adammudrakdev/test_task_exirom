package com.example.test_task_exirom.component.transaction.services.slave

import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.Acquirer
import org.springframework.stereotype.Component

@Component
class AcquirerRouter(val acquirerA: Acquirer, val acquirerB: Acquirer) {

    fun routeToAcquirer(transaction: Transaction): Acquirer {
        return when (verifyBinSum(transaction)) {
            true -> acquirerA
            else -> acquirerB
        }
    }

    private fun verifyBinSum(transaction: Transaction): Boolean {
        val binCardNumber = transaction.cardNumber.take(6)
        var sum = 0
        for (digit in 0 ..< binCardNumber.length) {
            sum += (binCardNumber[digit]).digitToInt()
        }

        return sum % 2 == 0
    }
}
