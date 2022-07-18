package com.deliveroo.parsing

import com.deliveroo.parsing.ExtensionFunctions.buildPrefix

private const val COMMAND = "command"

object CommandParser {

    private val prefix = COMMAND.buildPrefix()
    fun parse(commandField: String) = "$prefix $commandField"

}
