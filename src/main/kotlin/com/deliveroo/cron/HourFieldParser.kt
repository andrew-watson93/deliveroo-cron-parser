package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString
import com.deliveroo.errorhandling.InvalidCronException

object HourFieldParser {

    private val rangeRegex = Regex("^(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3])\$")
    private val stepRegex = Regex("^(\\*|(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3]))/(\\d|1[0-2])\$")
    private val listOfSpecificHoursRegex = Regex("^(?:\\d|1\\d|2[0-3])(?:,(?:\\d|1\\d|2[0-3]))*\$")
    private val defaultRange = Pair(0, 23)

    fun parse(hourField: String): String {
        if (hourField == "*") return "hour ${(0..23).toSpaceSeparatedString()}"
        if (rangeRegex.matches(hourField)) {
            val matchResult = rangeRegex.find(hourField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "hour ${(start..end).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(hourField)) {
            val matchResult = stepRegex.find(hourField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                defaultRange
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "hour ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (listOfSpecificHoursRegex.matches(hourField)) {
            return "hour ${hourField.split(",").toSpaceSeparatedString()}"
        }
        throw InvalidCronException("Invalid input for hours field")
    }

}