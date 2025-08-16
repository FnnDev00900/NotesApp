package ir.fnndev.notesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    val noteTitle: String,
    val noteContent: String
)
