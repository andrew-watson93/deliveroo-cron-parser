package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString

object DayOfMonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01])\$")

    fun parse(dayOfMonthField: String): String {
        if (rangeRegex.matches(dayOfMonthField)) {
            val matchResult = rangeRegex.find(dayOfMonthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "day of month ${(start..end).toSpaceSeparatedString()}"
        }
        return "day of month ${dayOfMonthField.replaceCommasWithSpaces()}"
    }
}