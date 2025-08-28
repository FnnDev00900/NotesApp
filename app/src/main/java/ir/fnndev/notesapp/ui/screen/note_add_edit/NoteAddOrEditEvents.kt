package ir.fnndev.notesapp.ui.screen.note_add_edit

import androidx.compose.ui.graphics.Color

sealed class NoteAddOrEditEvents {
    data class OnTitleChange(val title: String) : NoteAddOrEditEvents()
    data class OnContentChange(val content: String) : NoteAddOrEditEvents()
    data class OnNoteColorChange(val color: Color): NoteAddOrEditEvents()
    object OnSaveNote : NoteAddOrEditEvents()
}