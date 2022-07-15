package cron.deliveroo.cron

import com.deliveroo.cron.CronParser
import com.deliveroo.errorhandling.InvalidCronException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class CronParserTest {

    @Test
    fun `Should throw an exception if not enough arguments in String after split`() {
        val invalid = "*/15 0 1,15 * 1-5"
        val thrown = assertThrows(InvalidCronException::class.java) { CronParser.parse(invalid) }
        assertThat(thrown).hasMessage("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
    }

    @Test
    fun `Minutes field should support single digits`() {
        val minutes = "5"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 5")
    }

    @Test
    fun `Minutes field should support a list of digits`() {
        val minutes = "5,10,15"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 5 10 15")
    }

    @Test
    fun `Should throw an exception if trying to parse a range with a negative value`() {
        val minutes = "-1-5"
        val thrown = assertThrows(InvalidCronException::class.java) { CronParser.parseMinutes(minutes) }
        assertThat(thrown).hasMessage("Invalid input for minutes field")
    }

    @Test
    fun `Minutes field should support a range of digits greater than 0`() {
        val minutes = "0-5"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 0 1 2 3 4 5")
    }

    @Test
    fun `Minutes field should support a range of digits less than 60`() {
        val minutes = "55-59"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 55 56 57 58 59")
    }

    @Test
    fun `Minutes field should support a single asterisk`() {
        val minutes = "*"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute ${(0..59).joinToString(separator = " ")}")
    }

    @Test
    fun `Minutes field should support asterisk with a step`() {
        val minutes = "*/15"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 0 15 30 45")
    }

    @Test
    fun `Minutes field should support a step within a range`() {
        val minutes = "10-30/5"
        assertThat(CronParser.parseMinutes(minutes)).isEqualTo("minute 10 15 20 25 30")
    }
}