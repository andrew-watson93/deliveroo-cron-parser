package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DayOfMonthFieldParserTest {
    @Test
    fun `Day of month field should support a single day of the month`() {
        assertThat(DayOfMonthFieldParser.parse("4")).isEqualTo("day of month 4")
    }

    @Test
    fun `Day of month field should support multiple days of the month`() {
        assertThat(DayOfMonthFieldParser.parse("4,5,6,7")).isEqualTo("day of month 4 5 6 7")
    }

    @Test
    fun `Day of month field should support a range of digits greater than 1`() {
        assertThat(DayOfMonthFieldParser.parse("1-5")).isEqualTo("day of month 1 2 3 4 5")
    }

    @Test
    fun `Day of month field should support a range of digits less than 31`() {
        assertThat(DayOfMonthFieldParser.parse("29-31")).isEqualTo("day of month 29 30 31")
    }

    @Test
    fun `Day of month field should support a single asterisk`() {
        assertThat(DayOfMonthFieldParser.parse("*")).isEqualTo("day of month 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31")
    }

    @Test
    fun `Day of month field should support an asterisk with a step`(){
        assertThat(DayOfMonthFieldParser.parse("*/7")).isEqualTo("day of month 1 8 15 22 29")
    }

}