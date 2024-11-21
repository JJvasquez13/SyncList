package Data


import Notes
import Interfaces.IDBManager

class MemoryManager {
    /*private val notesList = mutableListOf<Notes>()

    override fun add(note: Notes) {
        if (notesList.any { it.id == note.id }) {
            throw IllegalArgumentException("A note with ID ${note.id} already exists.")
        }
        notesList.add(note)
    }

    override fun update(note: Notes) {
        val index = notesList.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notesList[index] = note
        } else {
            throw IllegalArgumentException("Cannot update: Note with ID ${note.id} does not exist.")
        }
    }

    override fun remove(id: String) {
        if (!notesList.removeIf { it.id == id }) {
            throw IllegalArgumentException("Cannot remove: Note with ID $id not found.")
        }
    }

    override fun getAll(): List<Notes> = notesList.toList()

    override fun getById(id: String): Notes? {
        return notesList.find { it.id == id }
    }

    override fun getBySyncNotes(share: Boolean): List<Notes> {
        return notesList.filter { it.share == share }
    }

    override fun getByMyNotes(share: Boolean): List<Notes> {
        // Devolviendo las notas que no son compartidas.
        return notesList.filter { !it.share }
    }*/
}
