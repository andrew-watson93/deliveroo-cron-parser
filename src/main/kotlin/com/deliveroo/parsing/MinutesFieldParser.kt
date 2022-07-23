package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException
import com.deliveroo.parsing.ExtensionFunctions.resultForCommaSeparatedList
import com.deliveroo.parsing.ExtensionFunctions.resultForTimeWindow


private const val MINUTE = "minute"

object MinutesFieldParser {

    //regex for a minutes field for a range of values, e.g. 5-10
    private val minutesRangeRegex = Regex("^(\\d|[0-5]\\d)-(\\d|[0-5]\\d)\$")

    //regex for a minutes field that contains a step, ie a slash, e.g. */15
    private val minutesStepRegex = Regex("^(\\*|(\\d|[0-5]\\d)-(\\d|[0-5]\\d))/(\\d|[0-5]\\d)\$")

    //regex for a minutes field that contains a single value or a comma separated list, e.g. 5 or 5,6,7,8
    private val listOfSpecificMinutesRegex = Regex("^(?:\\d|[1-5][\\d)])(?:,(?:\\d|[1-5][\\d)]))*\$")

    fun parse(minutesField: String): String {
        if (minutesField == "*") return MINUTE.resultForTimeWindow(0, 59)
        if (minutesStepRegex.matches(minutesField)) {
            val matchResult = minutesStepRegex.find(minutesField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(0, 59)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            validateMinuteRange(start, end)
            val stepMinutes = Integer.parseInt(matchResult.groupValues[4])
            return MINUTE.resultForTimeWindow(start, end, stepMinutes)
        }
        if (minutesRangeRegex.matches(minutesField)) {
            val matchResult = minutesRangeRegex.find(minutesField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateMinuteRange(start, end)
            return MINUTE.resultForTimeWindow(start, end)
        }
        if (listOfSpecificMinutesRegex.matches(minutesField)) {
            return MINUTE.resultForCommaSeparatedList(minutesField)
        }
        throw InvalidCronException("Invalid input for minutes field")
    }

    private fun validateMinuteRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start minute in range must be less than end minute")
        }
    }

}