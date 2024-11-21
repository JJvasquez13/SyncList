package Interfaces

import Notes

interface IDBManager {
    fun add(note: Notes)
    fun update(note: Notes)
    fun remove(id: String)
    fun getAll(): List<Notes>
    fun getById(id: String): Notes?
    fun getBySyncNotes(share: Boolean): List<Notes>?
    fun getByMyNotes(share: Boolean): List<Notes>?
}
