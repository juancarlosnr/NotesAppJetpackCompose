package com.example.notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSneakerDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesNotesDao(notesDatabase: NotesDatabase) = notesDatabase.noteDao()
}