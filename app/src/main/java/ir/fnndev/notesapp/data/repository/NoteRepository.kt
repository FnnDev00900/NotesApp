package ir.fnndev.notesapp.data.repository

import ir.fnndev.notesapp.data.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun upsertNote(note: Note)
    suspend fun deleteNote(note: Note)

    fun getNoteById(noteId: Int): Flow<Note?>
    fun getNotes(): Flow<List<Note>>

    fun getFilteredNotes(searchText: String): Flow<List<Note>>
}