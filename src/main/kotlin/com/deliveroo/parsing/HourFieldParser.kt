package com.deliveroo.parsing

import com.deliveroo.parsing.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.parsing.ExtensionFunctions.toSpaceSeparatedString
import com.deliveroo.errorhandling.InvalidCronException

private const val HOUR = "hour"

object HourFieldParser {

    private val rangeRegex = Regex("^(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3])\$")
    private val stepRegex = Regex("^(\\*|(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3]))/(\\d|1\\d|2[0-3])\$")
    private val listOfSpecificHoursRegex = Regex("^(?:\\d|1\\d|2[0-3])(?:,(?:\\d|1\\d|2[0-3]))*\$")
    private val prefix = "$HOUR${" ".repeat(14 - HOUR.length)}"

    fun parse(hourField: String): String {
        if (hourField == "*") return "$prefix ${(0..23).toSpaceSeparatedString()}"
        if (rangeRegex.matches(hourField)) {
            val matchResult = rangeRegex.find(hourField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateHourRange(start, end)
            return "$prefix ${(start..end).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(hourField)) {
            val matchResult = stepRegex.find(hourField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(0, 23)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            validateHourRange(start, end)
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$prefix ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (listOfSpecificHoursRegex.matches(hourField)) {
            return "$prefix ${hourField.replaceCommasWithSpaces()}"
        }
        throw InvalidCronException("Invalid input for hours field")
    }

    private fun validateHourRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start hour in range must be less than end hour")
        }
    }

}