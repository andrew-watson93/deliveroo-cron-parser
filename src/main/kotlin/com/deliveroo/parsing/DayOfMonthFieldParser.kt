package com.deliveroo.parsing

import com.deliveroo.errorhandling.InvalidCronException
import com.deliveroo.parsing.ExtensionFunctions.resultForCommaSeparatedList
import com.deliveroo.parsing.ExtensionFunctions.resultForTimeWindow

private const val DAY_OF_MONTH = "day of month"

object DayOfMonthFieldParser {

    private val rangeRegex = Regex("^([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01])\$")
    private val stepRegex = Regex("^(\\*|([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01]))/([1-9]|[12]\\d|3[01])\$")
    private val listOfSpecificDaysOfMonthRegex = Regex("^(?:[1-9]|[12]\\d|3[01])(?:,(?:[1-9]|[12]\\d|3[01]))*\$")


    fun parse(dayOfMonthField: String): String {
        if (dayOfMonthField == "*") {
            return DAY_OF_MONTH.resultForTimeWindow(1, 31)
        }
        if (stepRegex.matches(dayOfMonthField)) {
            val matchResult = stepRegex.find(dayOfMonthField)!!
            val (start, end) = if (matchResult.groupValues[1] == "*") {
                Pair(1, 31)
            } else {
                Pair(Integer.parseInt(matchResult.groupValues[2]), Integer.parseInt(matchResult.groupValues[3]))
            }
            validateMonthRange(start, end)
            val step = Integer.parseInt(matchResult.groupValues[4])
            return DAY_OF_MONTH.resultForTimeWindow(start, end, step)
        }
        if (rangeRegex.matches(dayOfMonthField)) {
            val matchResult = rangeRegex.find(dayOfMonthField)!!
            val start = Integer.parseInt(matchResult.groupValues[1])
            val end = Integer.parseInt(matchResult.groupValues[2])
            validateMonthRange(start, end)
            return DAY_OF_MONTH.resultForTimeWindow(start, end)
        }
        if (listOfSpecificDaysOfMonthRegex.matches(dayOfMonthField)) {
            return DAY_OF_MONTH.resultForCommaSeparatedList(dayOfMonthField)
        }
        throw InvalidCronException("Invalid input for day of month field")
    }

    private fun validateMonthRange(start: Int, end: Int) {
        if (start > end) {
            throw InvalidCronException("Start month in range must be less than end month")
        }
    }
}