package com.deliveroo.parsing

private const val COMMAND = "command"

object CommandParser {

    private val spaces = " ".repeat(14 - COMMAND.length)
    fun parse(commandField: String) = "$COMMAND$spaces $commandField"

}
