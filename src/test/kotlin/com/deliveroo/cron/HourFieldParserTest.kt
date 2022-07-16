package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HourFieldParserTest {

    @Test
    fun `Single hour fields are supported`(){
        assertThat(HourFieldParser.parse("0")).isEqualTo("hour 0")
    }

    @Test
    fun `Multiple hour fields separated by commas are supported`(){
        assertThat(HourFieldParser.parse("0,1,2,3")).isEqualTo("hour 0 1 2 3")
    }

    @Test
    fun `Minutes field should support a range of digits greater than 0`() {
        val hours = "0-5"
        assertThat(HourFieldParser.parse(hours)).isEqualTo("hour 0 1 2 3 4 5")
    }
}