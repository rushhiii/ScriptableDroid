package com.scriptabledroid.app

data class Script(
    val id: String,
    val name: String,
    val content: String,
    val description: String = "",
    val lastModified: Long = System.currentTimeMillis()
)