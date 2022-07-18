package com.deliveroo.parsing

private const val SPACE = " "
private const val COLUMNS = 14

object ExtensionFunctions {
    fun Iterable<Any>.toSpaceSeparatedString() = this.joinToString(separator = SPACE)
    fun String.replaceCommasWithSpaces() = this.replace(",", SPACE)
    fun String.buildPrefix() = "$this${SPACE.repeat(COLUMNS - this.length)}"
}