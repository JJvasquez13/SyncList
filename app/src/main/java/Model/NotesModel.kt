package Model

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import Notes
import android.content.Context
import DataBase
import java.io.ByteArrayOutputStream

class NotesModel(context: Context) {

    private val dbHelper = DataBase(context)

    // Convertir Bitmap a ByteArray
    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    // Convertir ByteArray a Bitmap
    fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    // Agregar una nueva nota con imagen
    fun addNote(note: Notes) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DataBase.COLUMN_ID, note.id)
            put(DataBase.COLUMN_NOTE_NAME, note.noteName)
            put(DataBase.COLUMN_DESCRIPTION, note.description)
            note.image?.let {
                put(DataBase.COLUMN_IMAGE, it)  // Insertar el ByteArray
            }
        }

        db.insert(DataBase.TABLE_NOTES, null, values)
        db.close()
    }

    // Recuperar una nota por ID
    fun getNote(id: String): Notes? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DataBase.TABLE_NOTES,
            arrayOf(DataBase.COLUMN_ID, DataBase.COLUMN_NOTE_NAME,
                DataBase.COLUMN_DESCRIPTION, DataBase.COLUMN_IMAGE),
            "${DataBase.COLUMN_ID} = ?",
            arrayOf(id), null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val noteId = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_ID))
            val noteName = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_NOTE_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_DESCRIPTION))
            val imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DataBase.COLUMN_IMAGE))  // Recuperar el ByteArray

            val note = Notes(noteId, noteName, description, imageBytes)
            cursor.close()
            db.close()
            return note
        }

        cursor?.close()
        db.close()
        return null
    }

    // Actualizar una nota existente
    fun updateNote(note: Notes) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DataBase.COLUMN_ID, note.id)
            put(DataBase.COLUMN_NOTE_NAME, note.noteName)
            put(DataBase.COLUMN_DESCRIPTION, note.description)
            note.image?.let {
                put(DataBase.COLUMN_IMAGE, it)  // Actualizar el ByteArray
            }
        }

        db.update(
            DataBase.TABLE_NOTES,
            values,
            "${DataBase.COLUMN_ID} = ?",
            arrayOf(note.id)
        )
        db.close()
    }

    // Eliminar una nota por ID
    fun deleteNote(id: String) {
        val db = dbHelper.writableDatabase
        db.delete(
            DataBase.TABLE_NOTES,
            "${DataBase.COLUMN_ID} = ?",
            arrayOf(id)
        )
        db.close()
    }

    // Verificar si la nota es duplicada
    fun isDuplicate(note: Notes): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DataBase.TABLE_NOTES,
            arrayOf(DataBase.COLUMN_ID),
            "${DataBase.COLUMN_ID} = ?",
            arrayOf(note.id), null, null, null
        )

        val isDuplicate = cursor?.moveToFirst() == true
        cursor?.close()
        db.close()
        return isDuplicate
    }

    // Obtener todas las notas
    fun getAllNotes(): List<Notes> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DataBase.TABLE_NOTES,
            arrayOf(DataBase.COLUMN_ID, DataBase.COLUMN_NOTE_NAME,
                DataBase.COLUMN_DESCRIPTION, DataBase.COLUMN_IMAGE),
            null, null, null, null, null
        )

        val notesList = mutableListOf<Notes>()
        while (cursor != null && cursor.moveToNext()) {
            val noteId = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_ID))
            val noteName = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_NOTE_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_DESCRIPTION))
            val imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DataBase.COLUMN_IMAGE))  // Obtener el ByteArray de la imagen

            val note = Notes(noteId, noteName, description, imageBytes)
            notesList.add(note)
        }

        cursor?.close()
        db.close()
        return notesList
    }
}
