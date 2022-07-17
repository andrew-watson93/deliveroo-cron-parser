package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces
import com.deliveroo.cron.ExtensionFunctions.toSpaceSeparatedString
import com.deliveroo.errorhandling.InvalidCronException


object MinutesFieldParser {

    //regex for a minutes field for a range of values, e.g. 5-10
    private val minutesRangeRegex = Regex("^(\\d|[0-5]\\d)-(\\d|[0-5]\\d)\$")

    //regex for a minutes field that contains a step, ie a slash, e.g. */15
    private val minutesStepRegex = Regex("^(\\*|(\\d|[0-5]\\d)-(\\d|[0-5]\\d))/(\\d|[1-2]\\d|30)\$")

    //regex for a minutes field that contains a single value or a comma separated list, e.g. 5 or 5,6,7,8
    private val listOfSpecificMinutesRegex = Regex("^(?:\\d|[1-5][\\d)])(?:,(?:\\d|[1-5][\\d)]))*\$")


    fun parse(minutesField: String): String {
        if (minutesField == "*") return minutesValuesWithStep()
        if (minutesStepRegex.matches(minutesField)) {
            val matchResult = minutesStepRegex.find(minutesField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(0, 59)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            val stepMinutes = Integer.parseInt(matchResult.groupValues[4])
            return minutesValuesWithStep(start, end, stepMinutes)
        }
        if (minutesRangeRegex.matches(minutesField)) {
            val matchResult = minutesRangeRegex.find(minutesField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            return minutesValuesWithStep(start, end)
        }
        if (listOfSpecificMinutesRegex.matches(minutesField)) {
            return "minute ${minutesField.replaceCommasWithSpaces()}"
        }
        throw InvalidCronException("Invalid input for minutes field")
    }

    private fun minutesValuesWithStep(start: Int = 0, end: Int = 59, step: Int = 1) =
        "minute ${(start..end step step).toSpaceSeparatedString()}"

}