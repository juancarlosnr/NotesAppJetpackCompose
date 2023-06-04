package com.example.notesapp.data

import com.example.notesapp.data.local.NoteEntity


fun Note.toNoteEntity() = NoteEntity(id = id, title = title, description = description)
fun NoteEntity.toNote() = Note(id = id, title = title, description = description)