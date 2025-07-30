package com.scriptabledroid.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scriptabledroid.app.databinding.ActivityScriptEditorBinding
import io.github.rosemoe.sora.langs.javascript.JavaScriptLanguage
import java.util.UUID

class ScriptEditorActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityScriptEditorBinding
    private lateinit var scriptStorage: ScriptStorage
    private lateinit var scriptEngine: ScriptEngine
    private var currentScript: Script? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScriptEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        scriptStorage = ScriptStorage(this)
        scriptEngine = ScriptEngine(this)
        
        setupCodeEditor()
        setupButtons()
        loadScript()
    }
    
    private fun setupCodeEditor() {
        binding.codeEditor.apply {
            setEditorLanguage(JavaScriptLanguage())
            setTextSize(14f)
        }
    }
    
    private fun setupButtons() {
        binding.buttonSave.setOnClickListener {
            saveScript()
        }
        
        binding.buttonRun.setOnClickListener {
            runScript()
        }
    }
    
    private fun loadScript() {
        val scriptId = intent.getStringExtra("script_id")
        if (scriptId != null) {
            currentScript = scriptStorage.getScript(scriptId)
            currentScript?.let { script ->
                binding.editTextScriptName.setText(script.name)
                binding.codeEditor.setText(script.content)
            }
        } else {
            // New script - provide a template
            binding.codeEditor.setText("""
                // Welcome to ScriptableDroid!
                // This is a JavaScript environment with access to Android APIs
                
                // Get battery information
                const batteryLevel = Device.batteryLevel();
                const batteryState = Device.batteryState();
                
                console.log("Battery: " + batteryLevel + "% (" + batteryState + ")");
                
                // Create a notification
                // Notification.create("My Title", "My message");
                
                // Return a value to display
                "Hello from ScriptableDroid!";
            """.trimIndent())
        }
    }
    
    private fun saveScript() {
        val name = binding.editTextScriptName.text.toString().trim()
        val content = binding.codeEditor.text.toString()
        
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a script name", Toast.LENGTH_SHORT).show()
            return
        }
        
        val script = Script(
            id = currentScript?.id ?: UUID.randomUUID().toString(),
            name = name,
            content = content,
            description = content.lines().firstOrNull { it.trim().startsWith("//") }?.trim()?.removePrefix("//")?.trim() ?: ""
        )
        
        currentScript = scriptStorage.saveScript(script)
        Toast.makeText(this, "Script saved!", Toast.LENGTH_SHORT).show()
    }
    
    private fun runScript() {
        val content = binding.codeEditor.text.toString()
        if (content.trim().isEmpty()) {
            binding.textViewOutput.text = "No script to run"
            return
        }
        
        val result = scriptEngine.executeScript(content)
        binding.textViewOutput.text = result
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scriptEngine.close()
    }
}