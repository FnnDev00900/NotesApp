package ir.fnndev.notesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.fnndev.notesapp.data.database.NoteDao
import ir.fnndev.notesapp.data.database.NoteDb
import ir.fnndev.notesapp.data.repository.NoteRepository
import ir.fnndev.notesapp.data.repository.NoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(@ApplicationContext context: Context): NoteDb =
        Room.databaseBuilder(
            context,
            NoteDb::class.java,
            "NoteDb"
        ).build()

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDb): NoteDao =
        database.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository =
        NoteRepositoryImpl(dao)
}