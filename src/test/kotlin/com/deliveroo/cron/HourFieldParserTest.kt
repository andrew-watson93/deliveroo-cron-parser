package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
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
}