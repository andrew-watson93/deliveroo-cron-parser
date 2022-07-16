package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
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
        val minutes = "0-6/2"
        assertThat(HourFieldParser.parse(minutes)).isEqualTo("hour 0 2 4 6")
    }
}