package com.example.test_task_exirom.common

object Constants {
    const val EXPIRY_MONTH_FIRST_DIGIT_INDEX = 0
    const val EXPIRY_MONTH_SECOND_DIGIT_INDEX = 1
    const val EXPIRY_DATE_THIRD_CHAR_DELIMITER = "/"
    const val EXPIRY_YEAR_FIRST_DIGIT_INDEX = 3
    const val EXPIRY_YEAR_SECOND_DIGIT_INDEX = 4
    const val MONTH_PREFIX_FOR_SINGLE_DIGIT = '0'
    const val OCTOBER_MONTH_NUMBER = 10
    const val YEAR_STRING_LAST_TWO_DIGITS_INDEX = 2

    const val BIN_END_INDEX = 6
    const val LAST_FOUR_START_INDEX = 12
    const val MASKED_CARD_MIDDLE_LENGTH = 6
    const val MASKER_CHAR = "*"
    const val MASKED_CVV = "***"
    const val MASKED_EXPIRY_DATE = "**/**"

    const val BASE_YEAR = 2000
    const val DATE_REGEX = "^(0[1-9]|1[0-2])/[0-9]{2}$"

    const val ACQUIRER_A = "AcquirerA"
    const val ACQUIRER_B = "AcquirerB"

    const val MIN = 0.1
    const val MAX = 1000000.0
    const val REDUCE_CEILING = 9

    const val CARD_DIGITS = "\\d+"
    const val CARD_SIZE = "^.{16}$"
    const val CVV_DIGITS_AND_SIZE_REGEX = "\\d{3}"
    const val MERCHANT_ID_CHARS_AND_SIZE_REGEX = "^\\S{3}$"
}