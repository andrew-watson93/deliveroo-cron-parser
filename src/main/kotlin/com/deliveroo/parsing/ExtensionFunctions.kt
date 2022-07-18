package com.deliveroo.parsing

private const val SPACE = " "

object ExtensionFunctions {
    fun Iterable<Any>.toSpaceSeparatedString() = this.joinToString(separator = SPACE)
    fun String.replaceCommasWithSpaces() = this.replace(",", " ")
}