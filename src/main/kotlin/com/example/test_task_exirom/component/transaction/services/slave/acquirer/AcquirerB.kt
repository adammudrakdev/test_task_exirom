package com.example.test_task_exirom.component.transaction.services.slave.acquirer

import org.springframework.stereotype.Service

@Service
class AcquirerB(acquirerA: Acquirer): Acquirer by acquirerA