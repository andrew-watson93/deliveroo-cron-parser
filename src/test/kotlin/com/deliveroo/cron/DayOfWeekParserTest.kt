package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DayOfWeekParserTest {

    @Test
    fun `Day of week field should support a single day`() {
        assertThat(DayOfWeekParser.parse("1")).isEqualTo("day of week 1")
    }

    @Test
    fun `Day of week field should support multiple days`() {
        assertThat(DayOfWeekParser.parse("1,2,3")).isEqualTo("day of week 1 2 3")
    }

    @Test
    fun `Day of week field should support a range of days greater than or equal to 1`() {
        assertThat(DayOfWeekParser.parse("2-5")).isEqualTo("day of week 2 3 4 5")
    }

    @Test
    fun `Day of week field should support a range of days less than or equal to 7`() {
        assertThat(DayOfWeekParser.parse("5-7")).isEqualTo("day of week 5 6 7")
    }

    @Test
    fun `Day of week field should support a single asterisk`() {
        assertThat(DayOfWeekParser.parse("*")).isEqualTo("day of week 1 2 3 4 5 6 7")
    }

    @Test
    fun `Day of week field should support an asterisk with a step`() {
        assertThat(DayOfWeekParser.parse("*/2")).isEqualTo("day of week 1 3 5 7")
    }

    @Test
    fun `Day of week field should support a range with a step`() {
        assertThat(DayOfWeekParser.parse("1-4/2")).isEqualTo("day of week 1 3")
    }
}