package com.example.test_task_exirom.exception

import com.example.test_task_exirom.dto.ErrorMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleCardValidationException(ex: CardValidationException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleMerchantNotFoundException(ex: MerchantNotFoundException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleNotValidAmountException(ex: NotValidAmountException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleTransactionNotFound(ex: TransactionNotFoundException): ResponseEntity<ErrorMessageDto> {

        val errorMessage = ErrorMessageDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorMessageDto> {
        val errorMessage = ErrorMessageDto(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.message!!
        )
        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errorsMap: MutableMap<String, Any> = LinkedHashMap()
        errorsMap["status"] = HttpStatus.BAD_REQUEST
        val errorMessagesList: List<String?> = ex.bindingResult.allErrors.map { error -> error.defaultMessage }.toList()
        errorsMap["errors"] = errorMessagesList
        return ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST)
    }
}