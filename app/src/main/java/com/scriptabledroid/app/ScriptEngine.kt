package com.scriptabledroid.app

import android.content.Context
import android.os.BatteryManager
import android.content.Intent
import android.content.IntentFilter
import com.squareup.duktape.Duktape
import org.json.JSONObject

class ScriptEngine(private val context: Context) {
    
    private val duktape = Duktape.create()
    
    init {
        setupScriptableAPI()
    }
    
    private fun setupScriptableAPI() {
        // Add console.log functionality
        duktape.set("console", object {
            fun log(message: String): String {
                return "LOG: $message"
            }
        })
        
        // Add battery info API
        duktape.set("Device", object {
            fun batteryLevel(): Double {
                val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                return if (level != -1 && scale != -1) {
                    (level * 100 / scale.toDouble())
                } else {
                    -1.0
                }
            }
            
            fun batteryState(): String {
                val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
                return when (status) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> "charging"
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> "discharging"
                    BatteryManager.BATTERY_STATUS_FULL -> "full"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "not_charging"
                    else -> "unknown"
                }
            }
        })
        
        // Add notification API placeholder
        duktape.set("Notification", object {
            fun create(title: String, body: String): String {
                // TODO: Implement actual notification creation
                return "Notification created: $title - $body"
            }
        })
    }
    
    fun executeScript(script: String): String {
        return try {
            val result = duktape.evaluate(script)
            result?.toString() ?: "Script executed successfully"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
    
    fun close() {
        duktape.close()
    }
}