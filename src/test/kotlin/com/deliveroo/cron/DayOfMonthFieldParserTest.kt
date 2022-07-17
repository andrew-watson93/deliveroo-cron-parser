package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DayOfMonthFieldParserTest {
    @Test
    fun `Day of month field should support a single day of the month`(){
        assertThat(DayOfMonthFieldParser.parse("4")).isEqualTo("day of month 4")
    }

    @Test
    fun `Day of month field should support multiple days of the month`(){
        assertThat(DayOfMonthFieldParser.parse("4,5,6,7")).isEqualTo("day of month 4 5 6 7")
    }
}