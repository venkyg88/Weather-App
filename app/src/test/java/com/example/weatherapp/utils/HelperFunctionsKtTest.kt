package com.example.weatherapp.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class HelperFunctionsKtTest {
    val unixDate: Int = 1680294547
    val unixDateTime1 = 1680270923
    val unixDateTime2 = 1680316161

    @Test
    fun `to test unit date format`() {
        val value = formatDate(unixDate)
        val expected = "Fri, Mar 31"
        assertTrue(value == expected)
    }

    @Test
    fun `to test date time format`() {
        val value = formatDateTime(unixDateTime1)
        val expected = "09:55:AM"
        assertTrue(value == expected)
    }

    @Test
    fun `to test to different times`() {
        assertTrue(formatDateTime(unixDateTime1) != formatDateTime(unixDateTime2))
    }
}