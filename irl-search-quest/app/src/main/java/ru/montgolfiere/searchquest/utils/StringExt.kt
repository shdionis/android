package ru.montgolfiere.searchquest.permissions.geo

import java.lang.StringBuilder

fun  Array<String>.printArray(): String {
    val sb = StringBuilder()
    this.forEach { sb.append("$it, ") }
    return sb.toString()
}