package com.deliveroo

import com.deliveroo.errorhandling.InvalidCronException
import com.deliveroo.parsing.CronParser

class CronApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                println(CronParser.parse(args))
            } catch (ex: InvalidCronException) {
                println("The cron you entered was invalid: ${ex.message}")
            }
        }
    }
}