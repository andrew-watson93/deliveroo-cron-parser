package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces

object DayOfMonthFieldParser {
    fun parse(dayOfMonthField: String): String {
        return "day of month ${dayOfMonthField.replaceCommasWithSpaces()}"
    }
}