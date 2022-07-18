package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException

object CronParser {
    fun parse(string: String): String {
        val split = string.split(" ")
        if (split.size != 6) {
            throw InvalidCronException("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
        }
        val minutes = MinutesFieldParser.parse(split[0])
        val hour = HourFieldParser.parse(split[1])
        val dayOfMonth = DayOfMonthFieldParser.parse(split[2])
        val month = MonthFieldParser.parse(split[3])
        val dayOfWeek = DayOfWeekParser.parse(split[4])
        val command = CommandParser.parse(split[5])
        return """$minutes
            |$hour
            |$dayOfMonth
            |$month
            |$dayOfWeek
            |$command
        """.trimMargin()
    }
}