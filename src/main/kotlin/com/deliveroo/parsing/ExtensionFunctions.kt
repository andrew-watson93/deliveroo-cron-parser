package com.deliveroo.parsing

private const val SPACE = " "
private const val COLUMNS = 14

object ExtensionFunctions {
    fun String.resultForTimeWindow(start: Int, end: Int, step: Int = 1) =
        "${this.buildPrefix()} ${(start..end step step).joinToString(separator = SPACE)}"

    fun String.resultForCommaSeparatedList(commaSeparated: String) =
        "${this.buildPrefix()} ${commaSeparated.replace(",", SPACE)}"

    fun String.buildPrefix() = "$this${SPACE.repeat(COLUMNS - this.length)}"

}