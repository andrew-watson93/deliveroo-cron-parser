package com.deliveroo.cron

import com.deliveroo.cron.ExtensionFunctions.replaceCommasWithSpaces

object MonthFieldParser {
    fun parse(monthField: String): String {
        return "month ${monthField.replaceCommasWithSpaces()}"
    }
}