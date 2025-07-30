package com.scriptabledroid.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import io.github.rosemoe.sora.langs.javascript.JavaScriptLanguage
import io.github.rosemoe.sora.widget.CodeEditor
import java.util.UUID

class ScriptEditorActivity : AppCompatActivity() {
    
    private lateinit var editTextScriptName: TextInputEditText
    private lateinit var codeEditor: CodeEditor
    private lateinit var buttonSave: Button
    private lateinit var buttonRun: Button
    private lateinit var textViewOutput: TextView
    private lateinit var scriptStorage: ScriptStorage
    private lateinit var scriptEngine: ScriptEngine
    private var currentScript: Script? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_script_editor)
        
        editTextScriptName = findViewById(R.id.editTextScriptName)
        codeEditor = findViewById(R.id.codeEditor)
        buttonSave = findViewById(R.id.buttonSave)
        buttonRun = findViewById(R.id.buttonRun)
        textViewOutput = findViewById(R.id.textViewOutput)
        
        scriptStorage = ScriptStorage(this)
        scriptEngine = ScriptEngine(this)
        
        setupCodeEditor()
        setupButtons()
        loadScript()
    }
    
    private fun setupCodeEditor() {
        codeEditor.apply {
            setEditorLanguage(JavaScriptLanguage())
            setTextSize(14f)
        }
    }
    
    private fun setupButtons() {
        buttonSave.setOnClickListener {
            saveScript()
        }
        
        buttonRun.setOnClickListener {
            runScript()
        }
    }
    
    private fun loadScript() {
        val scriptId = intent.getStringExtra("script_id")
        if (scriptId != null) {
            currentScript = scriptStorage.getScript(scriptId)
            currentScript?.let { script ->
                editTextScriptName.setText(script.name)
                codeEditor.setText(script.content)
            }
        } else {
            // New script - provide a template
            codeEditor.setText("""
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
        val name = editTextScriptName.text.toString().trim()
        val content = codeEditor.text.toString()
        
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
        val content = codeEditor.text.toString()
        if (content.trim().isEmpty()) {
            textViewOutput.text = "No script to run"
            return
        }
        
        val result = scriptEngine.executeScript(content)
        textViewOutput.text = result
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scriptEngine.close()
    }
}