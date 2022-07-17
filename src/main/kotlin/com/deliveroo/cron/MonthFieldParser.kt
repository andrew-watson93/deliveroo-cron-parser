package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString

private const val MONTH = "month"

object MonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|1[0-2])-([1-9]|1[0-2])\$")
    fun parse(monthField: String): String {
        if (rangeRegex.matches(monthField)) {
            val matchResult = rangeRegex.find(monthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "$MONTH ${(start..end).toSpaceSeparatedString()}"
        }
        return "$MONTH ${monthField.replaceCommasWithSpaces()}"
    }
}