package com.example.notesapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.data.Note
import com.example.notesapp.presentation.NotesViewModel

@Composable
fun MainScreen(notesViewModel: NotesViewModel = hiltViewModel()) {
    val notesList by notesViewModel.notes.collectAsState()

    var show by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NOTES APP",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(Modifier.fillMaxSize()) {
                items(notesList) { note ->
                    NoteCard(title = note.title, description = note.description)
                }
            }
        }
        MyCustomDialog(viewModel = notesViewModel, show = show, onDismiss = { show = false })
        FloatingActionButton(
            onClick = {
                show = true
            },
            containerColor = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            // Contenido del FloatingActionButton
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Icon Add",
                tint = Color.White
            )
        }
    }
}

@Composable
fun MyCustomDialog(
    viewModel: NotesViewModel,
    show: Boolean,
    onDismiss: () -> Unit
) {
    var title: String = ""
    var description: String = ""
    if (show) {
        Dialog(onDismissRequest = { onDismiss }) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleDialog()
                EditTextDialog(text = { title = it }, label = "Título")
                EditTextDialog(text = { description = it }, label = "Descripción")
                Button(onClick = {
                    viewModel.insertNote(Note(title = title, description = description))
                    onDismiss()
                }) {
                    Text(text = "Insertar nota", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TitleDialog() {
    Text(text = "Crea una nota", fontSize = 14.sp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextDialog(
    label: String,
    text: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            text(it.text)
        },
        label = { Text(text = label) },
        placeholder = { Text(text = label) },
    )
}

