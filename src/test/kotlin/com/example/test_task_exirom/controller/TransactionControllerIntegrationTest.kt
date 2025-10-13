package com.example.test_task_exirom.controller

import com.example.test_task_exirom.db.DbConnector
import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.enum.Currency
import com.example.test_task_exirom.enum.TransactionStatus
import com.example.test_task_exirom.service.utils.MAX
import com.example.test_task_exirom.service.utils.MIN
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.concurrent.atomic.AtomicLong

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionControllerIntegrationTest {
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeAll
    fun beforeAll(@Autowired webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @BeforeEach
    fun beforeEach() {
        DbConnector.database.clear()
        DbConnector.currentTransactionId = AtomicLong(0)
    }

    @Nested
    inner class ProcessTransaction {
        @DisplayName("Given valid transaction request to PrivatBank, approve such transaction")
        @Test
        fun givenValidRequestToPrivatBank_whenProcessTransaction_thenReturnAcceptedTransactionDto() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4000000000000002",
                "12/30",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedDto = GetTransactionDto(
                1L,
                "400000******0002",
                "**/**",
                "***",
                100.00,
                Currency.USD,
                "1qW",
                TransactionStatus.APPROVED)

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            //then
            val actualDto: GetTransactionDto = objectMapper.treeToValue(
                objectMapper.readTree(result.response.contentAsString),
                GetTransactionDto::class.java)

            assertEquals(expectedDto, actualDto)
        }

        @DisplayName("Given valid transaction request to OTPBank, approve such transaction")
        @Test
        fun givenValidRequestToOtpBank_whenProcessTransaction_thenReturnAcceptedTransactionDto() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111110000000",
                "01/26",
                "123",
                100.00,
                Currency.EUR,
                "2eR"))

            val expectedDto = GetTransactionDto(
                1L,
                "411111******0000",
                "**/**",
                "***",
                100.00,
                Currency.EUR,
                "2eR",
                TransactionStatus.APPROVED)

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            //then
            val actualDto: GetTransactionDto = objectMapper.treeToValue(
                objectMapper.readTree(result.response.contentAsString),
                GetTransactionDto::class.java)

            assertEquals(expectedDto, actualDto)
        }

        @DisplayName("Given invalid transaction request to OTPBank, deny such transaction")
        @Test
        fun givenInvalidRequestToOtpBank_whenProcessTransaction_thenReturnDeniedTransactionDto() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4012888888881881",
                "01/26",
                "123",
                100.00,
                Currency.UAH,
                "3tY"))

            val expectedDto = GetTransactionDto(
                1L,
                "401288******1881",
                "**/**",
                "***",
                100.00,
                Currency.UAH,
                "3tY",
                TransactionStatus.DENIED)

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            //then
            val actualDto: GetTransactionDto = objectMapper.treeToValue(
                objectMapper.readTree(result.response.contentAsString),
                GetTransactionDto::class.java)

            assertEquals(expectedDto, actualDto)
        }

        @DisplayName("Given invalid transaction request to PrivatBank, deny such transaction")
        @Test
        fun givenInvalidRequestToPrivatBank_whenProcessTransaction_thenReturnDeniedTransactionDto() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "5105105115125221",
                "12/30",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedDto = GetTransactionDto(
                1L,
                "510510******5221",
                "**/**",
                "***",
                100.00,
                Currency.USD,
                "1qW",
                TransactionStatus.DENIED)

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            //then
            val actualDto: GetTransactionDto = objectMapper.treeToValue(
                objectMapper.readTree(result.response.contentAsString),
                GetTransactionDto::class.java)

            assertEquals(expectedDto, actualDto)
        }

        @DisplayName("Given card number which fails Luhn test, throw exception")
        @Test
        fun givenInvalidCardNumberByLuhn_whenProcessTransaction_thenThrowCardValidationException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111112",
                "12/30",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "Invalid card number! Please try again..."

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
                .andReturn()

            //then
            val actualMessage: String = objectMapper.readTree(result.response.contentAsString).get("message").textValue()

            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given expired card, throw exception")
        @Test
        fun givenExpiredCardNumber_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/20",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "invalid expiryDate: must be after today and match pattern MM/YY"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given invalid format for card expiration, throw exception")
        @Test
        fun givenInvalidCardExpiry_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "2025-10",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "invalid expiryDate: must be after today and match pattern MM/YY"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given too long card number, throw exception")
        @Test
        fun givenTooLongCardNumber_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "1".repeat(17),
                "01/26",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "cardNumber: must be 16 digits long"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given too short card number, throw exception")
        @Test
        fun givenTooShortCardNumber_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "1".repeat(15),
                "01/26",
                "123",
                100.00,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "cardNumber: must be 16 digits long"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given wrong fraction for amount, throw exception")
        @Test
        fun givenWrongFractionForAmount_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "5105105115125221",
                "01/26",
                "123",
                100.375,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "amount: must match pattern 999999.99"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given too many integers for amount, throw exception")
        @Test
        fun givenTooManyIntegersForAmount_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "5105105115125221",
                "01/26",
                "123",
                10_000_000.0,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "amount: must match pattern 999999.99"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given amount that slightly exceeds ceiling, throw exception")
        @Test
        fun givenAmountAboveCeiling_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/26",
                "123",
                1_000_000.1,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "Amount must be inclusively from $MIN to $MAX"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("message").textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given amount that is slightly lower floor, throw exception")
        @Test
        fun givenAmountBelowFloor_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/26",
                "123",
                0.09,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "Amount must be inclusively from $MIN to $MAX"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("message").textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given merchant id which is too long, throw exception")
        @Test
        fun givenTooLongMerchantId_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/26",
                "123",
                0.1,
                Currency.USD,
                "1qW1"))

            val expectedExceptionMessage = "merchantId: must be 3 chars long, any chars allowed"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given merchant id which is too short, throw exception")
        @Test
        fun givenTooShortMerchantId_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/26",
                "123",
                0.1,
                Currency.USD,
                "1q"))

            val expectedExceptionMessage = "merchantId: must be 3 chars long, any chars allowed"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given merchant not from DB, throw exception")
        @Test
        fun givenMerchantNotFromDb_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "4111111111111111",
                "01/26",
                "123",
                0.1,
                Currency.USD,
                "6aS"))

            val expectedExceptionMessage = "Can't find merchant with id 6aS"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("message").textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }

        @DisplayName("Given blank values for almost all fields(except currency and amount), throw exception")
        @Test
        fun givenBlankValues_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "",
                "",
                "",
                0.09,
                Currency.USD,
                ""))

            val expectedExceptionMessages: List<String> = listOf("cardNumber: must not be blank",
                "expiryDate: must not be blank",
                "cvv: must not be blank",
                "merchantId: must not be blank")

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val errorBuilder: StringBuilder = StringBuilder()
            objectMapper.readTree(result.response.contentAsString).get("errors")
                .forEach { error -> errorBuilder.append(error.asText()).append(" ") }
            val errorString = errorBuilder.toString()


            for (expectedMessage in expectedExceptionMessages) {
                assertTrue(errorString.contains(expectedMessage))
            }
        }

        @DisplayName("Given card number that contains not only digits, throw exception")
        @Test
        fun givenCardNumberWithChars_whenProcessTransaction_thenThrowException() {
            //given
            val jsonRequest: String = objectMapper.writeValueAsString(TransactionDto(
                "1".repeat(8).plus(("a").repeat(4)).plus(("!").repeat(4)),
                "01/26",
                "123",
                0.09,
                Currency.USD,
                "1qW"))

            val expectedExceptionMessage = "cardNumber: must match pattern 0000000000000000"

            //when
            val result: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()

            //then
            val actualMessage = objectMapper.readTree(result.response.contentAsString).get("errors")[0].textValue()
            assertEquals(expectedExceptionMessage, actualMessage)
        }
    }
}