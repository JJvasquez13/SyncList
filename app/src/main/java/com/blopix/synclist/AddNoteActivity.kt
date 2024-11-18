package com.blopix.synclist

import Entities.Notes
import Model.NotesModel
import Util.util
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var btnAddPhoto: Button
    private lateinit var notesModel: NotesModel

    private var isEditionMode: Boolean = false
    private var photoPath: String = ""

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
        btnAddPhoto = findViewById(R.id.btnAddPhoto)

        val noteInfo = intent.getStringExtra("EXTRA_MESSAGE_NOTE_ID")
        if (noteInfo != null && noteInfo != "") loadNote(noteInfo.toString())

        btnSave.setOnClickListener { saveNote() }
        btnCancel.setOnClickListener {
            cleanForm()
            finish()
        }
        btnAddPhoto.setOnClickListener { showPhotoDialog() }
    }

    private fun showPhotoDialog() {
        val options = arrayOf(getString(R.string.openCamera), getString(R.string.btnCancel))
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.selectOption))
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> openCamera()
                1 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile: File? = createImageFile()
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.blopix.synclist.fileprovider",
                    photoFile
                )
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir).apply {
            photoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, getString(R.string.photoSaved), Toast.LENGTH_SHORT).show()
        } else {
            photoPath = ""
            Toast.makeText(this, getString(R.string.photoNotSaved), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveNote() {
        try {
            val note = Notes()
            note.id = txtId.text.toString()
            note.description = txtDescription.text.toString()
            note.icon = photoPath

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

    private fun cleanForm() {
        txtId.setText("")
        txtDescription.setText("")
        photoPath = ""
    }

    private fun loadNote(noteInfo: String) {
        try {
            val note = notesModel.getNote(noteInfo)
            txtId.setText(note.id)
            txtDescription.setText(note.description)
            photoPath = note.icon
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

    companion object {
        private const val CAMERA_REQUEST_CODE = 1
    }
}
