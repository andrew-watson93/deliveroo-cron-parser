package com.deliveroo.cron

object HourFieldParser {
    fun parse(hourField: String): String {
        return "hour $hourField"
    }

}