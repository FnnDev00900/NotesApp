package ir.fnndev.notesapp.data.repository

import ir.fnndev.notesapp.data.database.NoteDao
import ir.fnndev.notesapp.data.entity.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dao: NoteDao) : NoteRepository {
    override suspend fun upsertNote(note: Note) {
        dao.upsertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getFilteredNotes(searchText: String): Flow<List<Note>> {
        return dao.getFilteredNotes(searchText)
    }
}