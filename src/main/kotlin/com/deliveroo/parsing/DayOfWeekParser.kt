package com.deliveroo.parsing

import com.deliveroo.parsing.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.parsing.ExtensionFunctions.toSpaceSeparatedString
import com.deliveroo.errorhandling.InvalidCronException

private const val DAY_OF_WEEK = "day of week"

object DayOfWeekParser {

    private val rangeRegex = Regex("^([1-7])-([1-7])\$")
    private val stepRegex = Regex("^(\\*|([1-7])-([1-7]))/([1-7])\$")
    private val specificListOfDaysOfTheWeekRegex = Regex("^[1-7](?:,[1-7])*\$")


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
            validateDayOfWeekRange(start, end)
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$DAY_OF_WEEK ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (rangeRegex.matches(dayOfWeekField)) {
            val matchResult = rangeRegex.find(dayOfWeekField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateDayOfWeekRange(start, end)
            return "$DAY_OF_WEEK ${(start..end).toSpaceSeparatedString()}"
        }
        if (specificListOfDaysOfTheWeekRegex.matches(dayOfWeekField)) {
            return "$DAY_OF_WEEK ${dayOfWeekField.replaceCommasWithSpaces()}"
        }
        throw InvalidCronException("Invalid input for day of week field")
    }

    private fun validateDayOfWeekRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start day in range must be less than the end day")
        }
    }
}