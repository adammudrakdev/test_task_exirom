package com.example.test_task_exirom.component.transaction.services.slave

import com.example.test_task_exirom.common.Constants.ACQUIRER_A
import com.example.test_task_exirom.common.Constants.ACQUIRER_B
import com.example.test_task_exirom.common.Utils.isEven
import com.example.test_task_exirom.component.transaction.model.Transaction
import com.example.test_task_exirom.component.transaction.services.slave.acquirer.Acquirer
import org.springframework.stereotype.Component

@Component
class AcquirerRouter(val acquirers: List<Acquirer>) {

    fun routeToAcquirer(transaction: Transaction): Acquirer {
        return when (checkBinNumberSumEven(transaction)) {
            true -> acquirers.find { it.javaClass.simpleName.equals(ACQUIRER_A) }
            else -> acquirers.find { it.javaClass.simpleName.equals(ACQUIRER_B) }
        }!!
    }

    private fun checkBinNumberSumEven(transaction: Transaction): Boolean {
        val binCardNumber = transaction.cardNumber.take(6)
        val sum: Int = binCardNumber.map { it.digitToInt() }.sum()

        return sum.isEven()
    }
}
