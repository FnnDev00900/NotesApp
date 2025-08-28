package ir.fnndev.notesapp.data.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    val noteTitle: String,
    val noteContent: String,
    val noteColor: Int = Color.White.toArgb()
)
