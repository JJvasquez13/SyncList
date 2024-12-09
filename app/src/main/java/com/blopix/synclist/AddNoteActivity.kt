package com.blopix.synclist

import Notes
import Model.NotesModel
import Util.util
import android.os.Bundle
import android.text.InputFilter
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class AddNoteActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtDescription: EditText
    private lateinit var notesModel: NotesModel
    private var isEditionMode: Boolean = false

    // Si se incluye una imagen, esta variable la manejará
    private var photoBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Ajustar márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización
        notesModel = NotesModel(this)
        txtId = findViewById(R.id.txtId)
        txtName = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.multTxtDescription)

        setEditTextLimits()

        val noteInfo = intent.getStringExtra(EXTRA_MESSAGE_NOTE_ID)
        if (!noteInfo.isNullOrEmpty()) loadNote(noteInfo)
    }

    private fun setEditTextLimits() {
        txtId.filters = arrayOf(InputFilter.LengthFilter(10))
        txtName.filters = arrayOf(InputFilter.LengthFilter(50))
        txtDescription.filters = arrayOf(InputFilter.LengthFilter(250))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.crud_menu, menu)
        menu?.findItem(R.id.menu_delete)?.apply {
            isVisible = isEditionMode
            isEnabled = isEditionMode
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                saveNote()
                true
            }
            R.id.menu_delete -> {
                showConfirmationDialog(action = "delete")
                true
            }
            R.id.menu_cancel -> {
                cleanForm()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        try {
            // Convertir la imagen a ByteArray, si hay una foto seleccionada
            val imageByteArray: ByteArray? = photoBitmap?.let { convertBitmapToByteArray(it) }

            val notes = Notes(
                id = txtId.text.toString(),
                noteName = txtName.text.toString(),
                description = txtDescription.text.toString(),
                image = imageByteArray  // Guardamos la imagen si está disponible
            )

            if (dataValidation(notes)) {
                if (isEditionMode) {
                    showConfirmationDialog("update")
                } else {
                    if (notesModel.isDuplicate(notes)) {
                        Toast.makeText(this, R.string.msgDuplicateNote, Toast.LENGTH_LONG).show()
                    } else {
                        notesModel.addNote(notes)  // Guardar la nota en la base de datos
                        Toast.makeText(this, R.string.msgSaveNote, Toast.LENGTH_LONG).show()
                        util.openActivity(this, MainActivity::class.java)
                    }
                }
            } else {
                Toast.makeText(this, R.string.MsgMissingData, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun dataValidation(note: Notes): Boolean {
        return note.id.isNotEmpty() && note.noteName.isNotEmpty() &&
                note.description.isNotEmpty()
    }

    private fun showConfirmationDialog(action: String) {
        val message = when (action) {
            "delete" -> "¿Seguro que deseas eliminar la nota?"
            "update" -> "¿Seguro que deseas actualizar la nota?"
            else -> return
        }

        AlertDialog.Builder(this).apply {
            setTitle("Confirmación")
            setMessage(message)
            setPositiveButton("Sí") { _, _ ->
                when (action) {
                    "delete" -> performDeleteNote()
                    "update" -> performUpdateNote()
                }
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun performUpdateNote() {
        val notes = Notes(
            id = txtId.text.toString(),
            noteName = txtName.text.toString(),
            description = txtDescription.text.toString(),
            image = photoBitmap?.let { convertBitmapToByteArray(it) }  // Si existe una imagen, la convertimos
        )
        notesModel.updateNote(notes)
        Toast.makeText(this, R.string.msgUpdNote, Toast.LENGTH_LONG).show()
        util.openActivity(this, MainActivity::class.java)
    }

    private fun performDeleteNote() {
        notesModel.deleteNote(txtId.text.toString())
        Toast.makeText(this, R.string.deleteNote, Toast.LENGTH_LONG).show()
        cleanForm()
        util.openActivity(this, MainActivity::class.java)
    }

    private fun cleanForm() {
        txtId.text.clear()
        txtName.text.clear()
        txtDescription.text.clear()
        isEditionMode = false
        txtId.isEnabled = true
        invalidateOptionsMenu()
    }

    private fun loadNote(noteId: String) {
        notesModel.getNote(noteId)?.let {
            txtId.setText(it.id)
            txtName.setText(it.noteName)
            txtDescription.setText(it.description)
            photoBitmap = it.image?.let { byteArray -> convertByteArrayToBitmap(byteArray) }
            isEditionMode = true
            txtId.isEnabled = false
            invalidateOptionsMenu()
        } ?: Toast.makeText(this, R.string.msgNoteNotFound, Toast.LENGTH_LONG).show()
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
