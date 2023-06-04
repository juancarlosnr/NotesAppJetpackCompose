package com.example.notesapp.data

import com.example.notesapp.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
            .map { noteEntities ->
                noteEntities.map { it.toNote() }
            }
    }

    fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }

}