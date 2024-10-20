package com.blopix.synclist

import Entities.Notes
import Model.NotesModel
import Util.util
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddNoteActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var notesModel: NotesModel

    private var isEditionMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        notesModel = NotesModel(this)
        txtId = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.multTxtDescription)
        btnSave = findViewById(R.id.btnSaveAddNote)
        btnCancel = findViewById(R.id.btnCancelAddNote)

        val noteInfo = intent.getStringExtra("EXTRA_MESSAGE_NOTE_ID")
        if (noteInfo != null && noteInfo != "") loadNote(noteInfo.toString())

        btnSave.setOnClickListener {
            saveNote()
        }

        btnCancel.setOnClickListener {
            cleanForm()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.crud_menu, menu)

        if (isEditionMode) {
            menu?.findItem(R.id.menu_delete)?.isVisible = true
            menu?.findItem(R.id.menu_delete)?.isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        try {
            val note = Notes()
            note.id = txtId.text.toString()
            note.description = txtDescription.text.toString()

            if (dataValidation(note)) {
                if (isEditionMode) {
                    notesModel.updateNote(note)
                    util.openActivity(this, MainActivity::class.java)
                    Toast.makeText(this, R.string.msgUpdNote, Toast.LENGTH_LONG).show()
                } else {
                    notesModel.addNote(note)
                    cleanForm()
                    util.openActivity(this, MainActivity::class.java)
                    Toast.makeText(this, R.string.msgSaveNote, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.MsgMissingData, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteNote() {
        if (isEditionMode) {
            val noteId = txtId.text.toString()
            if (noteId.isNotEmpty()) {
                notesModel.remNote(noteId)
                cleanForm()
                util.openActivity(this, MainActivity::class.java)
                Toast.makeText(this, R.string.msgdelNote, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.msgInvalidId, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, R.string.msgIsNotInEditionMode, Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanForm() {
        txtId.setText("")
        txtDescription.setText("")
    }

    private fun loadNote(noteInfo: String) {
        try {
            val note = notesModel.getNote(noteInfo)
            txtId.setText(note.id)
            txtDescription.setText(note.description)
            isEditionMode = true
            txtId.isEnabled = false

            invalidateOptionsMenu()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun dataValidation(note: Notes): Boolean {
        return note.id.isNotEmpty() && note.description.isNotEmpty()
    }
}
