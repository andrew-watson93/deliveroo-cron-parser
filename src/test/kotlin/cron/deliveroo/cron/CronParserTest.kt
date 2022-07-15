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
}