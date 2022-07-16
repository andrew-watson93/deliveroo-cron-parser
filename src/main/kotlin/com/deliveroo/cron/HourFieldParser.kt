package com.deliveroo.cron

object HourFieldParser {

    private val rangeRegex = Regex("^(\\d|1\\d|2[0-3])-(\\d|1\\d|2[0-3])\$")

    fun parse(hourField: String): String {
        if (rangeRegex.matches(hourField)) {
            val matchResult = rangeRegex.find(hourField)
            val start = Integer.parseInt(matchResult!!.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return "hour ${(start..end).joinToString(separator = " ")}"
        }
        return "hour ${hourField.split(",").joinToString(separator = " ")}"
    }

}