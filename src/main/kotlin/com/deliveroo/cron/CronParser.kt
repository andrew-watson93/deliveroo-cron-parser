package com.deliveroo.cron

import com.deliveroo.errorhandling.InvalidCronException

object CronParser {

    private val minutesRangeRegex = Regex("^(\\d|[0-5]\\d)-(\\d|[0-5]\\d)\$")
    private val minutesStepRegex = Regex("^(\\*|(?:\\d|[0-5]\\d)-(?:\\d|[0-5]\\d))/(\\d|[1-2]\\d|30)\$")
    private val defaultMinutesRegex = Regex("^(\\d|[1-5][\\d)])(,\\d|[1-5][\\d)])*\$")

    fun parse(string: String) {
        throw InvalidCronException("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
    }

    fun parseMinutes(minutes: String): String {
        if (minutes == "*") return minutesStepString()
        if (minutesStepRegex.matches(minutes)) {
            val matchResult = minutesStepRegex.find(minutes)!!
            val stepRange = matchResult.groupValues[1]
            val stepMinutes = Integer.parseInt(matchResult.groupValues[2])
            val (start, end) = findRange(stepRange)
            return minutesStepString(start, end, stepMinutes)
        }
        if (minutesRangeRegex.matches(minutes)) {
            val matchResult = minutesRangeRegex.find(minutes)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return minutesStepString(start, end)
        }
        if (defaultMinutesRegex.matches(minutes)) {
            return "minute ${minutes.split(",").joinToString(separator = " ")}"
        }
        throw InvalidCronException("Invalid input for minutes field")
    }

    private fun findRange(stepRange: String): Pair<Int, Int> {
        if (stepRange == "*") return Pair(0, 59)
        val range = stepRange.split("-")
        return Pair(Integer.parseInt(range[0]), Integer.parseInt(range[1]))
    }

    private fun minutesStepString(start: Int = 0, end: Int = 59, stepMinutes: Int = 1) : String{
        return "minute ${(start..end step stepMinutes).joinToString(" ")}"
    }
}