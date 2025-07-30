package com.scriptabledroid.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView

class ScriptAdapter(
    private var scripts: List<Script>,
    private val onRunClick: (Script) -> Unit,
    private val onEditClick: (Script) -> Unit,
    private val onDeleteClick: (Script) -> Unit
) : RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder>() {
    
    class ScriptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewScriptName: TextView = itemView.findViewById(R.id.textViewScriptName)
        private val textViewScriptDescription: TextView = itemView.findViewById(R.id.textViewScriptDescription)
        private val buttonRun: Button = itemView.findViewById(R.id.buttonRun)
        private val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
        
        fun bind(
            script: Script,
            onRunClick: (Script) -> Unit,
            onEditClick: (Script) -> Unit,
            onDeleteClick: (Script) -> Unit
        ) {
            textViewScriptName.text = script.name
            textViewScriptDescription.text = script.description.ifEmpty { 
                script.content.lines().firstOrNull()?.take(50) ?: "No content"
            }
            
            buttonRun.setOnClickListener { onRunClick(script) }
            buttonEdit.setOnClickListener { onEditClick(script) }
            buttonDelete.setOnClickListener { onDeleteClick(script) }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_script, parent, false)
        return ScriptViewHolder(view)
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