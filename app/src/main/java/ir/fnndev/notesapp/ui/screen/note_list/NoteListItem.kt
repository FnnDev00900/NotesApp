package ir.fnndev.notesapp.ui.screen.note_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import ir.fnndev.notesapp.data.entity.Note
import ir.fnndev.notesapp.ui.theme.mediumDp
import ir.fnndev.notesapp.ui.theme.mediumSp
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.ui.theme.smallSp

@Composable
fun NoteListItem(note: Note, onEvent: (NoteListEvents) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = smallDp ,
                horizontal = mediumDp
            ),
        elevation = CardDefaults.cardElevation(mediumDp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumDp),
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
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.Green)
                    }
                    IconButton(onClick = {
                        onEvent(NoteListEvents.OnDeleteNote(note = note))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
            Text(text = note.noteContent, fontSize = smallSp, overflow = TextOverflow.Ellipsis, maxLines = 2)
        }
    }

}