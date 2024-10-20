package Data

import Entities.Notes
import Interfaces.IDBManager


object MemoryManager : IDBManager {
    private var notesList = mutableListOf<Notes>()
    override fun add(note: Notes) {
        notesList.add(note)
    }

    override fun update(note: Notes) {
        remove(note.id)
        notesList.add(note)
    }

    override fun remove(id: String) {
        notesList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getAll(): List<Notes> = notesList.toList()

    override fun getById(id: String): Notes? {
        try {
            var result = notesList.filter { (it.id) == id }
            return if (!result.any()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getBySyncNotes(share: Boolean): List<Notes>? {
        return if (share) {
            notesList.filter { it.share }
        } else {
            null
        }
    }

    override fun getByMyNotes(share: Boolean): List<Notes>? {
        return if (!share) {
            notesList.filter { !it.share }
        } else {
            null
        }
    }


}