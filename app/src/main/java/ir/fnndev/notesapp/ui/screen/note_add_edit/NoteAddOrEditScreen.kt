package ir.fnndev.notesapp.ui.screen.note_add_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.fnndev.notesapp.ui.theme.HoneyDew
import ir.fnndev.notesapp.ui.theme.Khaki
import ir.fnndev.notesapp.ui.theme.PaleTurquoise
import ir.fnndev.notesapp.ui.theme.PeachPuff
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

    val noteColors = listOf(Khaki, PaleTurquoise, HoneyDew, PeachPuff)
    val backgroundColor = viewModel.noteColor.collectAsState()

    LaunchedEffect(key1 = noteId) { viewModel.updateNoteId(noteId) }
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
            .background(backgroundColor.value)
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
                )
                .background(backgroundColor.value),
            verticalArrangement = Arrangement.SpaceBetween,
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
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                value = content.value,
                onValueChange = { viewModel.onEvents(NoteAddOrEditEvents.OnContentChange(it)) },
                label = { Text(text = "Content") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                singleLine = false,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .weight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                noteColors.forEach { noteColor ->
                    NoteColor(noteColor, onColorChange = {
                        viewModel.onEvents(NoteAddOrEditEvents.OnNoteColorChange(noteColor))
                    })
                }
            }
        }
    }
}

@Composable
fun NoteColor(color: Color, onColorChange: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .padding(smallDp)
            .clip(shape = CircleShape)
            .border(width = 0.5.dp, color = Color.Black, shape = CircleShape)
            .background(color = color)
            .clickable(onClick = { onColorChange() })
    )
}