package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString

private const val DAY_OF_MONTH = "day of month"

object DayOfMonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01])\$")
    private val stepRegex = Regex("^(\\*|([1-9]|[12]\\d|3[01])-(1-9|[12]\\d|3[01]))/([1-9]|[12]\\d|3[01])\$")

    fun parse(dayOfMonthField: String): String {
        if (dayOfMonthField == "*") {
            return "$DAY_OF_MONTH ${(1..31).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(dayOfMonthField)) {
            val matchResult = stepRegex.find(dayOfMonthField)!!
            val (start,end) =  if (matchResult.groupValues[1] == "*") {
                Pair(1,31)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$DAY_OF_MONTH ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (rangeRegex.matches(dayOfMonthField)) {
            val matchResult = rangeRegex.find(dayOfMonthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "$DAY_OF_MONTH ${(start..end).toSpaceSeparatedString()}"
        }
        return "$DAY_OF_MONTH ${dayOfMonthField.replaceCommasWithSpaces()}"
    }
}