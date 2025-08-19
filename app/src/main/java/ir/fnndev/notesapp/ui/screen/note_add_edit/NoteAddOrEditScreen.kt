package ir.fnndev.notesapp.ui.screen.note_add_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.utils.UiEvents

@Composable
fun NoteAddOrEditScreen(
    noteId: Int,
    navController: NavController,
    viewModel: NoteAddOrEditViewModel = hiltViewModel()
) {

    val title = viewModel.titleState.collectAsState()
    val content = viewModel.contentState.collectAsState()

    LaunchedEffect(key1 = noteId) { viewModel.updateNoteId(noteId)}
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.PopBack -> {
                    navController.popBackStack()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(smallDp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvents(NoteAddOrEditEvents.OnSaveNote)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Add New")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + smallDp,
                    bottom = innerPadding.calculateBottomPadding() + smallDp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title.value,
                onValueChange = { viewModel.onEvents(NoteAddOrEditEvents.OnTitleChange(it)) },
                label = { Text(text = "Title") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(smallDp))
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = content.value,
                onValueChange = { viewModel.onEvents(NoteAddOrEditEvents.OnContentChange(it)) },
                label = { Text(text = "Content") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                singleLine = false,
            )
        }
    }
}