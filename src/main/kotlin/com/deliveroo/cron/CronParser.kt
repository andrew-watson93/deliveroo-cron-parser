package com.deliveroo.cron

import com.deliveroo.errorhandling.InvalidCronException

object CronParser {
    fun parse(string: String) {
        val split = string.split(" ")
        if (split.size != 6) {
            throw InvalidCronException("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
        }
        val minutes = MinutesFieldParser.parseMinutes(split[0])
    }
}