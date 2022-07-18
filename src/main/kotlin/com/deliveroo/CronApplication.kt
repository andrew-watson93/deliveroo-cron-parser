package com.deliveroo

import com.deliveroo.parsing.CronParser

class CronApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(CronParser.parse(args))
        }
    }
}