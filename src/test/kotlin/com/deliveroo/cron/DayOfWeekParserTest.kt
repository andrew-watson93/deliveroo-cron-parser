package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DayOfWeekParserTest {

    @Test
    fun `Day of week field should support a single day`() {
        assertThat(DayOfWeekParser.parse("1")).isEqualTo("day of week 1")
    }
}