package ir.fnndev.notesapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ir.fnndev.notesapp.data.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NoteTable")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM NoteTable WHERE noteTitle LIKE :searchText OR noteContent LIKE :searchText")
    fun getFilteredNotes(searchText: String): Flow<List<Note>>
}