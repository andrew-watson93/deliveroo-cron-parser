package com.deliveroo.cron

import com.deliveroo.errorhandling.InvalidCronException
import com.deliveroo.parsing.HourFieldParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class HourFieldParserTest {

    @Test
    fun `Single hour fields are supported`() {
        assertThat(HourFieldParser.parse("0")).isEqualTo("hour 0")
    }

    @Test
    fun `Multiple hour fields separated by commas are supported`() {
        assertThat(HourFieldParser.parse("0,1,2,3")).isEqualTo("hour 0 1 2 3")
    }

    @Test
    fun `Minutes field should support a range of digits greater than 0`() {
        val hours = "0-5"
        assertThat(HourFieldParser.parse(hours)).isEqualTo("hour 0 1 2 3 4 5")
    }

    @Test
    fun `Hours field should support a single asterisk`() {
        assertThat(HourFieldParser.parse("*")).isEqualTo("hour 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23")
    }

    @Test
    fun `Hours field should support asterisk with a step`() {
        val hours = "*/4"
        assertThat(HourFieldParser.parse(hours)).isEqualTo("hour 0 4 8 12 16 20")
    }

    @Test
    fun `Hours field should support a step within a range`() {
        val hours = "0-6/2"
        assertThat(HourFieldParser.parse(hours)).isEqualTo("hour 0 2 4 6")
    }

    @Test
    fun `Should throw an exception for hours outside of the accepted range (0-23)`() {
        val thrown = assertThrows(InvalidCronException::class.java) { HourFieldParser.parse("24") }
        assertThat(thrown).hasMessage("Invalid input for hours field")
    }

    @Test
    fun `Should throw an exception for an unexpected format`() {
        val thrown = assertThrows(InvalidCronException::class.java) { HourFieldParser.parse("//*12") }
        assertThat(thrown).hasMessage("Invalid input for hours field")
    }

    @Test
    fun `Should throw an exception for a range if start hour is greater than end hour`() {
        val thrown = assertThrows(InvalidCronException::class.java) { HourFieldParser.parse("12-10") }
        assertThat(thrown).hasMessage("Start hour in range must be less than end hour")
    }

    @Test
    fun `Should throw an exception for a range with a step if start month is greater than end month`() {
        val thrown = assertThrows(InvalidCronException::class.java) { HourFieldParser.parse("12-10/2") }
        assertThat(thrown).hasMessage("Start hour in range must be less than end hour")
    }
}