package com.example.test_task_exirom.service.utils

import com.example.test_task_exirom.db.DbConnector
import com.example.test_task_exirom.exception.MerchantNotFoundException

object MerchantExistsChecker {
    fun doesMerchantExist(merchantId: String) {
        if (!DbConnector.merchantDatabase.contains(merchantId)) {
            throw MerchantNotFoundException("Can't find merchant with id $merchantId")
        }
    }
}