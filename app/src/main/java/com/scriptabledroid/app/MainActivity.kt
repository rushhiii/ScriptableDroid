package com.scriptabledroid.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.scriptabledroid.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var scriptStorage: ScriptStorage
    private lateinit var scriptEngine: ScriptEngine
    private lateinit var scriptAdapter: ScriptAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        scriptStorage = ScriptStorage(this)
        scriptEngine = ScriptEngine(this)
        
        setupRecyclerView()
        setupFab()
        
        // Create default scripts on first run
        scriptStorage.createDefaultScripts()
        loadScripts()
    }
    
    private fun setupRecyclerView() {
        scriptAdapter = ScriptAdapter(
            scripts = emptyList(),
            onRunClick = { script -> runScript(script) },
            onEditClick = { script -> editScript(script) },
            onDeleteClick = { script -> confirmDeleteScript(script) }
        )
        
        binding.recyclerViewScripts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = scriptAdapter
        }
    }
    
    private fun setupFab() {
        binding.fabNewScript.setOnClickListener {
            createNewScript()
        }
    }
    
    private fun loadScripts() {
        val scripts = scriptStorage.getAllScripts()
        scriptAdapter.updateScripts(scripts)
        
        binding.textViewEmpty.visibility = if (scripts.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerViewScripts.visibility = if (scripts.isEmpty()) View.GONE else View.VISIBLE
    }
    
    private fun runScript(script: Script) {
        val result = scriptEngine.executeScript(script.content)
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Script Output: ${script.name}")
            .setMessage(result)
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun editScript(script: Script) {
        val intent = Intent(this, ScriptEditorActivity::class.java).apply {
            putExtra("script_id", script.id)
        }
        startActivity(intent)
    }
    
    private fun createNewScript() {
        val intent = Intent(this, ScriptEditorActivity::class.java)
        startActivity(intent)
    }
    
    private fun confirmDeleteScript(script: Script) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Script")
            .setMessage("Are you sure you want to delete '${script.name}'?")
            .setPositiveButton("Delete") { _, _ ->
                scriptStorage.deleteScript(script.id)
                loadScripts()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun onResume() {
        super.onResume()
        loadScripts()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scriptEngine.close()
    }
}