package com.example.test_task_exirom.controller

import com.example.test_task_exirom.db.DbConnector
import com.example.test_task_exirom.dto.GetTransactionDto
import com.example.test_task_exirom.dto.TransactionDto
import com.example.test_task_exirom.enum.Currency
import com.example.test_task_exirom.enum.TransactionStatus
import com.fasterxml.jackson.databind.ObjectMapper
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
import kotlin.test.assertEquals

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
    }
}