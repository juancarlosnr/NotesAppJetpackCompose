package com.example.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.local.NoteDao
import com.example.notesapp.data.local.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}