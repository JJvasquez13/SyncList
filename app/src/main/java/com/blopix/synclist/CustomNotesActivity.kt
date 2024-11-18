package com.blopix.synclist

import Adapter.NotesAdapter
import Model.NotesModel
import Util.util
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blopix.synclist.R

class CustomNotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_notes)

        // Configuración de las vistas para ajuste de márgenes y pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el modelo de notas
        val noteModel = NotesModel(this)
        val lstNote =
            findViewById<ListView>(R.id.viewCustomNotesList)  // Asumiendo que tienes un ListView en el XML

        // Obtener la lista de notas
        val notesList = noteModel.getNotes()

        // Crear y configurar el adaptador
        val adapter = NotesAdapter(this, R.layout.list_item, notesList)
        lstNote.adapter = adapter

        // Configurar el listener para los clics en los ítems
        lstNote.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val noteId = notesList[position].id
                util.openActivity(
                    this,
                    AddNoteActivity::class.java,
                    EXTRA_MESSAGE_NOTE_ID,
                    noteId
                )
            }
    }
}
