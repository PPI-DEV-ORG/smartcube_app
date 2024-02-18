package com.ppidev.smartcube.utils

import org.junit.Assert.*

import org.junit.Test

class EmailValidationTest {
    @Test
    fun `test valid email`() {
        val validEmails = listOf(
            "test@example.com",
            "user1234@test.co.uk",
            "john.doe@company.net"
        )
        validEmails.forEach {
            assertTrue(validateEmail(it))
        }
    }

    @Test
    fun `test invalid email`() {
        val invalidEmails = listOf(
            "invalid_email.com",
            "user@domain",
            "user@.com",
            "user@domain..com",
            "user@domain.c",
            "@test.com",
            "user@.com.",
            ".user@domain.com"
        )
        invalidEmails.forEach {
            assertFalse(validateEmail(it))
        }
    }
}

class PasswordValidationTest {

    @Test
    fun `password length less than 8 should return false`() {
        assertFalse(validatePassword("abc123"))
    }

    @Test
    fun `password length exactly 8 should return true`() {
        assertTrue(validatePassword("password"))
    }

    @Test
    fun `password length greater than 8 should return true`() {
        assertTrue(validatePassword("securePW123"))
    }

    @Test
    fun `empty password should return false`() {
        assertFalse(validatePassword(""))
    }
}

class PercentageParsingTest {

    @Test
    fun `valid percentage string should return correct float value`() {
        assertEquals(25f, getFloatFromPercentageString("25%"))
        assertEquals(50.5f, getFloatFromPercentageString("50.5%"))
        assertEquals(100f, getFloatFromPercentageString("100%"))
    }

    @Test
    fun `percentage string without percent sign should return zero`() {
        assertEquals(50f, getFloatFromPercentageString("50"))
    }

    @Test
    fun `invalid percentage string should return zero`() {
        assertEquals(0f, getFloatFromPercentageString("abc%"))
        assertEquals(0f, getFloatFromPercentageString("%$#^&"))
    }

    @Test
    fun `empty percentage string should return zero`() {
        assertEquals(0f, getFloatFromPercentageString(""))
    }
}

class TimeConversionTest {

    @Test
    fun `convert milliseconds to hours and minutes`() {
        assertEquals("01 hours 00 minutes", convertMillisecondsToHoursAndMinutes(3600000))
        assertEquals("01 hours 30 minutes", convertMillisecondsToHoursAndMinutes(5400000))
        assertEquals("02 hours 45 minutes", convertMillisecondsToHoursAndMinutes(9900000))
        assertEquals("00 hours 45 minutes", convertMillisecondsToHoursAndMinutes(2700000))
    }

    @Test
    fun `milliseconds less than 1 minute should return 00 hours 00 minutes`() {
        assertEquals("00 hours 00 minutes", convertMillisecondsToHoursAndMinutes(500))
        assertEquals("00 hours 00 minutes", convertMillisecondsToHoursAndMinutes(0))
        assertEquals("00 hours 00 minutes", convertMillisecondsToHoursAndMinutes(59999))
    }

    @Test
    fun `milliseconds equal to 1 hour should return 01 hours 00 minutes`() {
        assertEquals("01 hours 00 minutes", convertMillisecondsToHoursAndMinutes(3600000))
    }

    @Test
    fun `milliseconds equal to 1 minute should return 00 hours 01 minutes`() {
        assertEquals("00 hours 01 minutes", convertMillisecondsToHoursAndMinutes(60000))
    }
}

class NumberExtractionTest {

    @Test
    fun `extract number from string with valid format should return correct number`() {
        assertEquals(123L, extractNumberFromString("123 apples")!!)
        assertEquals(456L, extractNumberFromString("456 oranges")!!)
        assertEquals(789L, extractNumberFromString("789 bananas")!!)
    }

    @Test
    fun `extract number from string with invalid format should return null`() {
        println("test : "+ extractNumberFromString("1.23"))
        assertNull(extractNumberFromString("abc"))
        assertNull(extractNumberFromString("1.23"))
        assertNull(extractNumberFromString("12.34 apples"))
        assertNull(extractNumberFromString("apples 123"))
    }

    @Test
    fun `extract number from empty string should return null`() {
        assertNull(extractNumberFromString(""))
    }

    @Test
    fun `extract number from string with negative number should return correct number`() {
        assertEquals(-123L, extractNumberFromString("-123 apples")!!)
        assertEquals(-456L, extractNumberFromString("-456 oranges")!!)
        assertEquals(-789L, extractNumberFromString("-789 bananas")!!)
    }
}

class FloatExtractionTest {

    @Test
    fun `extract float from string with valid format should return correct float value`() {
        assertEquals(123.45f, extractFloatFromString("The price is $123.45")!!)
        assertEquals(0.5f, extractFloatFromString("The value is 0.5")!!)
        assertEquals(3.14f, extractFloatFromString("Pi is approximately 3.14")!!)
    }

    @Test
    fun `extract float from string with invalid format should return null`() {
        println("test : "+extractFloatFromString("123"))
        assertNull(extractFloatFromString("abc"))
        assertNull(extractFloatFromString("12.34.56"))
        assertNull(extractFloatFromString("12.34.56 dollars"))
    }

    @Test
    fun `extract float from empty string should return null`() {
        assertNull(extractFloatFromString(""))
    }

    @Test
    fun `extract float from string with negative float should return correct float value`() {
        assertEquals(-1.5f, extractFloatFromString("The temperature is -1.5 degrees Celsius")!!)
        assertEquals(123.45f, extractFloatFromString("-$123.45")!!)
    }
}

class DateFormatConversionTest {

    @Test
    fun `convert ISO date format to string date format`() {
        assertEquals("01-01-2022 00:00:00", isoDateFormatToStringDate("2022-01-01T00:00:00.000Z"))
        assertEquals("18-02-2024 12:34:56", isoDateFormatToStringDate("2024-02-18T12:34:56.789Z"))
    }

    @Test
    fun `convert invalid ISO date format should return '-'`() {
        assertEquals("-", isoDateFormatToStringDate("invalid_date"))
        assertEquals("-", isoDateFormatToStringDate("2022-01-01"))
    }

    @Test
    fun `convert empty ISO date format should return '-'`() {
        assertEquals("-", isoDateFormatToStringDate(""))
    }
}

class IsoDateToEpochTest {

    @Test
    fun `convert ISO date string to epoch seconds`() {
        assertEquals(1643673600L, isoDateToEpoch("2022-02-01T00:00:00Z"))
        assertEquals(1710765296L, isoDateToEpoch("2024-03-18T12:34:56Z"))
    }

    @Test
    fun `convert invalid ISO date string should return null`() {
        assertNull(isoDateToEpoch("invalid_date"))
        assertNull(isoDateToEpoch("2022-01-01"))
    }

    @Test
    fun `convert empty ISO date string should return null`() {
        assertNull(isoDateToEpoch(""))
    }
}
