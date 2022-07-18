package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class MonthFieldParserTest {

    @Test
    fun `Month field should support a single month`() {
        assertThat(MonthFieldParser.parse("5")).isEqualTo("month          5")
    }

    @Test
    fun `Month field should support multiple months`() {
        assertThat(MonthFieldParser.parse("5,6,7")).isEqualTo("month          5 6 7")
    }

    @Test
    fun `Month field should support a range of digits greater than or equal to 1`() {
        assertThat(MonthFieldParser.parse("1-5")).isEqualTo("month          1 2 3 4 5")
    }

    @Test
    fun `Month field should support a range of digits less than or equal to 12`() {
        assertThat(MonthFieldParser.parse("10-12")).isEqualTo("month          10 11 12")
    }

    @Test
    fun `Month field should support a single asterisk`() {
        assertThat(MonthFieldParser.parse("*")).isEqualTo("month          1 2 3 4 5 6 7 8 9 10 11 12")
    }

    @Test
    fun `Month field should support an asterisk with a step`() {
        assertThat(MonthFieldParser.parse("*/2")).isEqualTo("month          1 3 5 7 9 11")
    }

    @Test
    fun `Month field should support a month range with a step`() {
        assertThat(MonthFieldParser.parse("4-8/2")).isEqualTo("month          4 6 8")
    }

    @Test
    fun `Should throw an exception for an invalid month`() {
        val thrown = assertThrows(InvalidCronException::class.java) { MonthFieldParser.parse("13") }
        assertThat(thrown).hasMessage("Invalid input for month field")
    }

    @Test
    fun `Should throw an exception for a range if start month is greater than end month`() {
        val thrown = assertThrows(InvalidCronException::class.java) { MonthFieldParser.parse("12-10") }
        assertThat(thrown).hasMessage("Start month in range must be less than end month")
    }

    @Test
    fun `Should throw an exception for a range with a step if start month is greater than end month`() {
        val thrown = assertThrows(InvalidCronException::class.java) { MonthFieldParser.parse("12-10/2") }
        assertThat(thrown).hasMessage("Start month in range must be less than end month")
    }
}