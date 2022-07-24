package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException
import com.deliveroo.parsing.ExtensionFunctions.resultForCommaSeparatedList
import com.deliveroo.parsing.ExtensionFunctions.resultForTimeWindow

private const val HOUR = "hour"

object HourFieldParser {

    private val rangeRegex = Regex("^(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3])\$")
    private val stepRegex = Regex("^(\\*|(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3]))/(\\d|1\\d|2[0-3])\$")
    private val listOfSpecificHoursRegex = Regex("^(?:\\d|1\\d|2[0-3])(?:,(?:\\d|1\\d|2[0-3]))*\$")

    fun parse(hourField: String): String {
        if (hourField == "*") return HOUR.resultForTimeWindow(0, 23)
        if (rangeRegex.matches(hourField)) {
            val matchResult = rangeRegex.find(hourField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateHourRange(start, end)
            return HOUR.resultForTimeWindow(start, end)
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
            return HOUR.resultForTimeWindow(start, end, step)
        }
        if (listOfSpecificHoursRegex.matches(hourField)) {
            return HOUR.resultForCommaSeparatedList(hourField)
        }
        throw InvalidCronException("Invalid input for hours field")
    }

    private fun validateHourRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start hour in range must be less than end hour")
        }
    }

}