package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString
import com.deliveroo.errorhandling.InvalidCronException

private const val MONTH = "month"

object MonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|1[0-2])-([1-9]|1[0-2])\$")
    private val stepRegex = Regex("^(\\*|([1-9]|1[0-2])-([1-9]|1[0-2]))/([1-9]|1[0-2])\$")
    private val listOfSpecificMonthsRegex = Regex("^(?:[1-9]|1[0-2])(?:,(?:[1-9]|1[0-2]))*\$")

    fun parse(monthField: String): String {
        if (monthField == "*") {
            return "$MONTH ${(1..12).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(monthField)) {
            val matchResult = stepRegex.find(monthField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(1, 12)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            validateMonthRange(start, end)
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$MONTH ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (rangeRegex.matches(monthField)) {
            val matchResult = rangeRegex.find(monthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateMonthRange(start, end)
            return "$MONTH ${(start..end).toSpaceSeparatedString()}"
        }
        if (listOfSpecificMonthsRegex.matches(monthField)) {
            return "$MONTH ${monthField.replaceCommasWithSpaces()}"
        }
        throw InvalidCronException("Invalid input for month field")
    }

    private fun validateMonthRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start month in range must be less than end month")
        }
    }
}