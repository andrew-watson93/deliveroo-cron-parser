package com.deliveroo.cron

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MonthFieldParserTest {
    @Test
    fun `Month field should support a single month`(){
        assertThat(MonthFieldParser.parse("5")).isEqualTo("month 5")
    }
}