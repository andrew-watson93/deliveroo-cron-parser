package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException

object CronParser {
    fun parse(args: Array<String>): String {
        if (args.size != 6) {
            throw InvalidCronException("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
        }
        val minutes = MinutesFieldParser.parse(args[0])
        val hour = HourFieldParser.parse(args[1])
        val dayOfMonth = DayOfMonthFieldParser.parse(args[2])
        val month = MonthFieldParser.parse(args[3])
        val dayOfWeek = DayOfWeekParser.parse(args[4])
        val command = CommandParser.parse(args[5])
        return """$minutes
            |$hour
            |$dayOfMonth
            |$month
            |$dayOfWeek
            |$command
        """.trimMargin()
    }
}