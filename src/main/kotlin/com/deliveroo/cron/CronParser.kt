package com.deliveroo.cron

import com.deliveroo.errorhandling.InvalidCronException

object CronParser {

    //regex for a minutes field for a range of values, e.g. 5-10
    private val minutesRangeRegex = Regex("^(?:\\d|[0-5]\\d)-(?:\\d|[0-5]\\d)\$")

    //regex for a minutes field that contains a step, ie a slash, e.g. */15
    private val minutesStepRegex = Regex("^(\\*|(?:\\d|[0-5]\\d)-(?:\\d|[0-5]\\d))/(\\d|[1-2]\\d|30)\$")

    //regex for a minutes field that contains a single value or a comma separated list, e.g. 5 or 5,6,7,8
    private val listOfSpecificMinutesRegex = Regex("^(?:\\d|[1-5][\\d)])(?:,(?:\\d|[1-5][\\d)]))*\$")

    fun parse(string: String) {
        throw InvalidCronException("Invalid cron statement, statement must have 6 fields (minute, hour, day of month, month, day of week, command)")
    }

    fun parseMinutes(minutes: String): String {
        if (minutes == "*") return minutesValuesWithStep()
        if (minutesStepRegex.matches(minutes)) {
            val matchResult = minutesStepRegex.find(minutes)!!
            val stepMinutes = Integer.parseInt(matchResult.groupValues[2])
            val (start, end) = findRange(matchResult.groupValues[1])
            return minutesValuesWithStep(start, end, stepMinutes)
        }
        if (minutesRangeRegex.matches(minutes)) {
            val matchResult = minutesRangeRegex.find(minutes)!!
            val (start, end) = findRange(matchResult.groupValues[0])
            return minutesValuesWithStep(start, end)
        }
        if (listOfSpecificMinutesRegex.matches(minutes)) {
            return "minute ${minutes.split(",").joinToString(separator = " ")}"
        }
        throw InvalidCronException("Invalid input for minutes field")
    }

    private fun findRange(stepRange: String): Pair<Int, Int> {
        if (stepRange == "*") return Pair(0, 59)
        val range = stepRange.split("-")
        return Pair(Integer.parseInt(range[0]), Integer.parseInt(range[1]))
    }

    private fun minutesValuesWithStep(start: Int = 0, end: Int = 59, step: Int = 1) =
        "minute ${(start..end step step).joinToString(" ")}"
}