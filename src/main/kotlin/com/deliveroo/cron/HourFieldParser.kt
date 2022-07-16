package com.deliveroo.cron

private const val SPACE = " "

object HourFieldParser {

    private val rangeRegex = Regex("^(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3])\$")
    private val stepRegex = Regex("^(\\*|(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3]))/(\\d|1[0-2])\$")

    fun parse(hourField: String): String {
        if (hourField == "*") return "hour ${(0..23).joinToString(separator = SPACE)}"
        if (rangeRegex.matches(hourField)) {
            val matchResult = rangeRegex.find(hourField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "hour ${(start..end).joinToString(separator = SPACE)}"
        }
        if (stepRegex.matches(hourField)) {
            val matchResult = stepRegex.find(hourField)!!
            val step = Integer.parseInt(matchResult.groupValues[4])
            return "hour ${(0..23 step step).joinToString(separator = SPACE)}"
        }
        return "hour ${hourField.split(",").joinToString(separator = SPACE)}"
    }

}