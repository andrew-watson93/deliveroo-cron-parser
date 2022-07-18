package cron.deliveroo.cron

import com.deliveroo.parsing.CronParser
import com.deliveroo.errorhandling.InvalidCronException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class CronParserTest {

    @Test
    fun `Should throw an exception if not enough arguments in String after split`() {
        val invalid = "*/15 0 1,15 * 1-5"
        val thrown = assertThrows(InvalidCronException::class.java) { CronParser.parse(invalid.split(" ").toTypedArray()) }
        assertThat(thrown).hasMessage("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
    }

    @Test
    fun `Should build output in expected format with one description of cron per line`() {
        val expected = "minute         0 15 30 45\nhour           0\nday of month   1 15\nmonth          1 2 3 4 5 6 7 8 9 10 11 12\nday of week    1 2 3 4 5\ncommand        /usr/bin/find"
        assertThat(CronParser.parse("*/15 0 1,15 * 1-5 /usr/bin/find".split(" ").toTypedArray())).isEqualTo(expected)
    }
}