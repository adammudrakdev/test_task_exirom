package com.example.test_task_exirom.common.exception

import com.example.test_task_exirom.component.transaction.exception.CardValidationException
import com.example.test_task_exirom.component.transaction.exception.MerchantNotFoundException
import com.example.test_task_exirom.component.transaction.exception.NotValidAmountException
import com.example.test_task_exirom.component.transaction.exception.TransactionNotFoundException
import com.example.test_task_exirom.component.transaction.services.master.TransactionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)

    @ExceptionHandler
    fun handleCardValidationException(ex: CardValidationException): ResponseEntity<ErrorMessageDto> {
        logger.warn(ex.message)
        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message!!)

        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleMerchantNotFoundException(ex: MerchantNotFoundException): ResponseEntity<ErrorMessageDto> {
        logger.warn(ex.message)
        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!)

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleNotValidAmountException(ex: NotValidAmountException): ResponseEntity<ErrorMessageDto> {
        logger.warn(ex.message)
        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message!!)

        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleTransactionNotFound(ex: TransactionNotFoundException): ResponseEntity<ErrorMessageDto> {
        logger.warn(ex.message)
        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!)

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorMessageDto> {
        logger.warn(ex.message)
        val errorMessage = ErrorMessageDto(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.message!!)

        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        logger.warn(ex.message)
        val errorsMap: MutableMap<String, Any> = LinkedHashMap()
        errorsMap["status"] = HttpStatus.BAD_REQUEST
        val errorMessagesList: List<String?> = ex.bindingResult.allErrors.map { error -> error.defaultMessage }.toList()
        errorsMap["errors"] = errorMessagesList

        return ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST)
    }

    data class ErrorMessageDto(val status: Int, val message: String)
}