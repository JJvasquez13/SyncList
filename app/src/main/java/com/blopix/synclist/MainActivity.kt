package com.blopix.synclist

import Model.NotesModel
import Util.util
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val EXTRA_MESSAGE_NOTE_ID = "com.blopix.synclist.NOTE_ID"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuración de la vista para los márgenes de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el modelo de notas
        val noteModel = NotesModel(this)
        val lstNote = findViewById<ListView>(R.id.lstNoteList)

        // Obtener la lista de las notas de la base de datos
        val notes = noteModel.getAllNotes()

        // Usar un LayoutInflater para crear las vistas manualmente
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Limpiar el ListView antes de agregar los elementos
        lstNote.removeAllViews()

        // Iterar a través de las notas y agregar cada vista al ListView
        for (note in notes) {
            // Inflar el layout para cada elemento de la lista
            val rowView = inflater.inflate(R.layout.list_item, lstNote, false)

            // Asignar el texto (ID y nombre) al TextView
            val textView = rowView.findViewById<TextView>(R.id.note_id)
            textView.text = "${note.id} - ${note.noteName}"

            // Añadir la vista al ListView
            lstNote.addView(rowView)
        }

        // Configurar el listener para los clics en los ítems
        lstNote.setOnItemClickListener { parent, view, position, id ->
            val itemValue = notes[position]  // Obtener la nota completa
            util.openActivity(
                this,
                AddNoteActivity::class.java,
                EXTRA_MESSAGE_NOTE_ID,
                itemValue.id.toString()  // Pasar el ID como String
            )
        }

        // Manejar la acción de agregar una nueva nota
        val btnPantallaAgregar: Button = findViewById<Button>(R.id.add_note_button)
        btnPantallaAgregar.setOnClickListener {
            util.openActivity(this, AddNoteActivity::class.java)
            Toast.makeText(
                this,
                getString(R.string.msgWinAdd),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.my_Note -> {
                util.openActivity(this, MainActivity::class.java)
                true
            }

            R.id.my_Note_Custom -> {
                util.openActivity(this, CustomNotesActivity::class.java)
                true
            }

            R.id.viewSyncNotes -> {
                util.openActivity(this, SyncListActivity::class.java)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
