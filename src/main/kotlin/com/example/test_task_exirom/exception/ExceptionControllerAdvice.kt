package com.example.test_task_exirom.exception

import com.example.test_task_exirom.dto.ErrorMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleTransactionNotFound(ex: TransactionNotFoundException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleCardValidationException(ex: CardValidationException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleTransactionDeniedException(ex: TransactionDeniedException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }
}