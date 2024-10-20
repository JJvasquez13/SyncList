package Model

import Data.MemoryManager
import Entities.Notes
import Interfaces.IDBManager
import android.content.Context
import android.content.res.Resources
import com.blopix.synclist.R

class NotesModel {
    private var dbManager: IDBManager = MemoryManager
    private lateinit var _context: Context

    constructor(context: Context) {
        _context = context
    }

    fun addNote(notes: Notes) {
        dbManager.add(notes)
    }

    fun getNotes() = dbManager.getAll()

    fun getNote(id: String): Notes {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.msgNoteNotFound)
            throw Exception(message)
        }
        return result
    }


    fun remNote(id: String) {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.msgNoteNotFound)
            throw Exception(message)
        }
        dbManager.remove(id)
    }

    fun updateNote(notes: Notes) {
        dbManager.update(notes)
    }

    // Método para obtener notas compartidas
    fun getBySyncNotes(share: Boolean): List<Notes>? {
        return dbManager.getBySyncNotes(share)
    }

    // Método para obtener mis notas no compartidas
    fun getByMyNotes(share: Boolean): List<Notes>? {
        return dbManager.getByMyNotes(share)
    }
}