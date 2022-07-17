package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces

object DayOfWeekParser {
    fun parse(dayOfWeekField: String): String {
        return "day of week ${dayOfWeekField.replaceCommasWithSpaces()}"
    }
}