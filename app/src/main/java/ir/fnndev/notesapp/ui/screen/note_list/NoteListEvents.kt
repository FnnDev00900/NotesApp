package ir.fnndev.notesapp.ui.screen.note_list

import ir.fnndev.notesapp.data.entity.Note

sealed class NoteListEvents {
    data class OnDeleteNote(val note: Note) : NoteListEvents()
    data class OnEditNote(val note: Note) : NoteListEvents()
    data class OnSearchTextChange(val searchText: String) : NoteListEvents()
}