package com.deliveroo.parsing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CommandParserTest {
    @Test
    fun `Command field is returned`(){
        assertThat(CommandParser.parse("/whatever/run")).isEqualTo("command        /whatever/run")
    }
}