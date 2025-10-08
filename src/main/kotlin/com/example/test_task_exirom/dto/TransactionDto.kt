package com.example.test_task_exirom.dto

import com.example.test_task_exirom.enum.Currency
import java.time.LocalDate

data class TransactionDto(val cardNumber: String, val expiryDate: LocalDate, val cvv: String,
                          val amount: Double, val currency: Currency, val merchantId: String)
