package com.scriptabledroid.app

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

class ScriptStorage(private val context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("scripts", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun saveScript(script: Script): Script {
        val scripts = getAllScripts().toMutableList()
        val existingIndex = scripts.indexOfFirst { it.id == script.id }
        
        val updatedScript = if (existingIndex >= 0) {
            scripts[existingIndex] = script.copy(lastModified = System.currentTimeMillis())
            scripts[existingIndex]
        } else {
            val newScript = script.copy(
                id = if (script.id.isEmpty()) UUID.randomUUID().toString() else script.id,
                lastModified = System.currentTimeMillis()
            )
            scripts.add(newScript)
            newScript
        }
        
        saveScripts(scripts)
        return updatedScript
    }
    
    fun deleteScript(scriptId: String) {
        val scripts = getAllScripts().filter { it.id != scriptId }
        saveScripts(scripts)
    }
    
    fun getAllScripts(): List<Script> {
        val scriptsJson = prefs.getString("scripts_list", "[]")
        val type = object : TypeToken<List<Script>>() {}.type
        return gson.fromJson(scriptsJson, type) ?: emptyList()
    }
    
    fun getScript(scriptId: String): Script? {
        return getAllScripts().find { it.id == scriptId }
    }
    
    private fun saveScripts(scripts: List<Script>) {
        val scriptsJson = gson.toJson(scripts)
        prefs.edit().putString("scripts_list", scriptsJson).apply()
    }
    
    fun createDefaultScripts() {
        if (getAllScripts().isEmpty()) {
            val batteryScript = Script(
                id = UUID.randomUUID().toString(),
                name = "Battery Info",
                content = """
                    // Get battery information
                    const batteryLevel = Device.batteryLevel();
                    const batteryState = Device.batteryState();
                    
                    console.log("Battery Level: " + batteryLevel + "%");
                    console.log("Battery State: " + batteryState);
                    
                    "Battery: " + batteryLevel + "% (" + batteryState + ")";
                """.trimIndent(),
                description = "Display current battery level and charging state"
            )
            
            val notificationScript = Script(
                id = UUID.randomUUID().toString(),
                name = "Test Notification",
                content = """
                    // Create a test notification
                    const result = Notification.create("ScriptableDroid", "Hello from JavaScript!");
                    console.log(result);
                    result;
                """.trimIndent(),
                description = "Create a test notification"
            )
            
            saveScript(batteryScript)
            saveScript(notificationScript)
        }
    }
}