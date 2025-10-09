package com.example.test_task_exirom.service.subservices

import com.example.test_task_exirom.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class OtpBankPaymentServiceImpl(transactionRepository: TransactionRepository): CommonPaymentService(transactionRepository, "OTP")