package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString

private const val MONTH = "month"

object MonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|1[0-2])-([1-9]|1[0-2])\$")
    private val stepRegex = Regex("^(\\*|([1-9]|1[0-2])-([1-9]|1[0-2]))/([1-9]|1[0-2])\$")
    fun parse(monthField: String): String {
        if (monthField == "*") {
            return "$MONTH ${(1..12).toSpaceSeparatedString()}"
        }
        if (stepRegex.matches(monthField)) {
            val matchResult = stepRegex.find(monthField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(1,12)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "$MONTH ${(start..end step step).toSpaceSeparatedString()}"
        }
        if (rangeRegex.matches(monthField)) {
            val matchResult = rangeRegex.find(monthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "$MONTH ${(start..end).toSpaceSeparatedString()}"
        }
        return "$MONTH ${monthField.replaceCommasWithSpaces()}"
    }
}