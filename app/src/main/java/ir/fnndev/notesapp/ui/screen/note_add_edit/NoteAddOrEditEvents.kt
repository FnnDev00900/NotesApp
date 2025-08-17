package ir.fnndev.notesapp.ui.screen.note_add_edit

sealed class NoteAddOrEditEvents {
    data class OnTitleChange(val title: String) : NoteAddOrEditEvents()
    data class OnContentChange(val content: String) : NoteAddOrEditEvents()
    object OnSaveNote : NoteAddOrEditEvents()
}