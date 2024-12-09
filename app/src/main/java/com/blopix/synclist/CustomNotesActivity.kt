package com.blopix.synclist

import adapter.NotesListAdapter
import Model.NotesModel
import Util.util
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomNotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Para pantallas sin bordes
        setContentView(R.layout.activity_custom_notes)

        // Configuración de las vistas para ajuste de márgenes y pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el modelo de notas
        val noteModel = NotesModel(this)
        val lstNote = findViewById<ListView>(R.id.viewCustomNotesList)

        // Obtener la lista de todas las notas
        val notesList = noteModel.getAllNotes() // Cambiado a getAllNotes() para obtener todas las notas

        // Crear y configurar el adaptador
        val adapter = NotesListAdapter(this, R.layout.list_item, notesList)
        lstNote.adapter = adapter

        // Configurar el listener para los clics en los ítems
        lstNote.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val noteId = notesList[position].id // Obtener el id de la nota seleccionada

            // Abrir AddNoteActivity para editar la nota con el id seleccionado
            util.openActivity(
                this,
                AddNoteActivity::class.java,
                EXTRA_MESSAGE_NOTE_ID,
                noteId // Pasa el id de la nota
            )
        }
    }
}
