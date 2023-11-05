package ru.montgolfier.comments

import java.io.InputStreamReader
import java.util.*

object PropertiesManager {
    private val properties: Properties by lazy { initProperties() }

    private fun initProperties(): Properties {
        val props = Properties()
        val resource = Thread.currentThread().contextClassLoader.getResourceAsStream("comments.backend.properties")
        resource?.let {
            props.load(InputStreamReader(it))
        }
        return props
    }

    fun getProperty(key: String) = properties[key]?.let { it as String }
}