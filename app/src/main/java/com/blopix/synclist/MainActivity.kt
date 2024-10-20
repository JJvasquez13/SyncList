package com.blopix.synclist

import Model.NotesModel
import Util.util
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val EXTRA_MESSAGE_NOTE_ID = "com.blopix.myapplication.noteId"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val noteModel = NotesModel(this)
        val lstNote = findViewById<ListView>(R.id.lstNoteList)

        // Obtener la lista de IDs de las notas
        val noteIds = noteModel.getNotes().map { it.id }

        // Crear un ArrayAdapter personalizado
        val adapter = object : ArrayAdapter<String>(this, R.layout.list_item, noteIds) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowView = inflater.inflate(R.layout.list_item, parent, false)

                // Asignar el ID de la nota al TextView
                val textView = rowView.findViewById<TextView>(R.id.note_id)
                textView.text = noteIds[position]
                return rowView
            }
        }

        // Asignar el adaptador al ListView
        lstNote.adapter = adapter
        lstNote.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemValue = lstNote.getItemAtPosition(position) as String
                util.openActivity(
                    this,
                    AddNoteActivity::class.java,
                    EXTRA_MESSAGE_NOTE_ID,
                    itemValue
                )
            }


        val btnPantallaAgregar: Button = findViewById<Button>(R.id.add_note_button)
        btnPantallaAgregar.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, AddNoteActivity::class.java)

            Toast.makeText(
                this,
                getString(R.string.msgWinAdd).toString(),
                Toast.LENGTH_LONG
            ).show()
        })
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
                return true
            }

            R.id.viewSyncNotes -> {
                util.openActivity(this, SyncListActivity::class.java)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}