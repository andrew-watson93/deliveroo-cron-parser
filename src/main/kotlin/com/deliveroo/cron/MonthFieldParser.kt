package com.deliveroo.cron

object MonthFieldParser {
    fun parse(monthField: String): String {
        return "month $monthField"
    }
}