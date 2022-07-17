package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString

private const val DAY_OF_WEEK = "day of week"

object DayOfWeekParser {

    private val rangeRegex = Regex("^([1-7])-([1-7])\$")
    private val stepRegex = Regex("^(\\*|([1-7])-([1-7]))/([1-7])\$")


    fun parse(dayOfWeekField: String): String {
        if (dayOfWeekField == "*") {
            return "$DAY_OF_WEEK ${(1..7).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(dayOfWeekField)) {
            val matchResult = stepRegex.find(dayOfWeekField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(1, 7)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$DAY_OF_WEEK ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (rangeRegex.matches(dayOfWeekField)) {
            val matchResult = rangeRegex.find(dayOfWeekField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "$DAY_OF_WEEK ${(start..end).toSpaceSeparatedString()}"
        }
        return "$DAY_OF_WEEK ${dayOfWeekField.replaceCommasWithSpaces()}"
    }
}