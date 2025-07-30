package com.scriptabledroid.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scriptabledroid.app.databinding.ItemScriptBinding

class ScriptAdapter(
    private var scripts: List<Script>,
    private val onRunClick: (Script) -> Unit,
    private val onEditClick: (Script) -> Unit,
    private val onDeleteClick: (Script) -> Unit
) : RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder>() {
    
    class ScriptViewHolder(private val binding: ItemScriptBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            script: Script,
            onRunClick: (Script) -> Unit,
            onEditClick: (Script) -> Unit,
            onDeleteClick: (Script) -> Unit
        ) {
            binding.textViewScriptName.text = script.name
            binding.textViewScriptDescription.text = script.description.ifEmpty { 
                script.content.lines().firstOrNull()?.take(50) ?: "No content"
            }
            
            binding.buttonRun.setOnClickListener { onRunClick(script) }
            binding.buttonEdit.setOnClickListener { onEditClick(script) }
            binding.buttonDelete.setOnClickListener { onDeleteClick(script) }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptViewHolder {
        val binding = ItemScriptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScriptViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ScriptViewHolder, position: Int) {
        holder.bind(scripts[position], onRunClick, onEditClick, onDeleteClick)
    }
    
    override fun getItemCount(): Int = scripts.size
    
    fun updateScripts(newScripts: List<Script>) {
        scripts = newScripts
        notifyDataSetChanged()
    }
}