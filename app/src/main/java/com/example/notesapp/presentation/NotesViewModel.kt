package com.example.notesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow(emptyList<Note>())

    val notes = _notes.asStateFlow()

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch { //this: CoroutineScope
            noteRepository.getAllNotes().flowOn(Dispatchers.IO).collect { notes: List<Note> ->
                _notes.update { notes }
            }
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }

    }
}