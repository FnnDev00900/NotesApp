package ir.fnndev.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.fnndev.notesapp.data.entity.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDb : RoomDatabase(){
    abstract fun noteDao(): NoteDao
}