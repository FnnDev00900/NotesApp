package ir.fnndev.notesapp.ui.screen.note_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import ir.fnndev.notesapp.data.entity.Note
import ir.fnndev.notesapp.ui.theme.mediumSp
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.ui.theme.smallSp

@Composable
fun NoteListItem(note: Note, onEvent: (NoteListEvents) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(smallDp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallDp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = note.noteTitle, fontSize = mediumSp)
            Row {
                IconButton(onClick = {
                    onEvent(NoteListEvents.OnEditNote(note = note))
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {
                    onEvent(NoteListEvents.OnDeleteNote(note = note))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        Text(text = note.noteContent, fontSize = smallSp, overflow = TextOverflow.Ellipsis, maxLines = 2)
    }
}