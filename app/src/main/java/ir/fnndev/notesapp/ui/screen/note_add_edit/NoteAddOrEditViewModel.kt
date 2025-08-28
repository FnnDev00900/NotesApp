package ir.fnndev.notesapp.ui.screen.note_add_edit

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.fnndev.notesapp.data.entity.Note
import ir.fnndev.notesapp.data.repository.NoteRepository
import ir.fnndev.notesapp.utils.UiEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddOrEditViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {
    private var note: Note? = null

    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private val _noteId = MutableStateFlow(-1)
    val noteId = _noteId.asStateFlow()

    private val _titleState = MutableStateFlow("")
    val titleState = _titleState.asStateFlow()

    private val _contentState = MutableStateFlow("")
    val contentState = _contentState.asStateFlow()

    private val _noteColor = MutableStateFlow(Color.White)
    val noteColor = _noteColor.asStateFlow()

    fun onEvents(events: NoteAddOrEditEvents) {
        when (events) {
            is NoteAddOrEditEvents.OnContentChange -> _contentState.value = events.content
            NoteAddOrEditEvents.OnSaveNote -> {
                if (noteId.value == -1) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val newNote = Note(
                            noteId = 0,
                            noteTitle = _titleState.value,
                            noteContent = _contentState.value,
                            noteColor = _noteColor.value.toArgb()
                        )
                        repository.upsertNote(newNote)
                        _uiEvents.emit(UiEvents.PopBack)
                    }
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        val updateNote = Note(
                            noteId = _noteId.value,
                            noteTitle = _titleState.value,
                            noteContent = _contentState.value,
                            noteColor = _noteColor.value.toArgb()
                        )
                        repository.upsertNote(updateNote)
                        _uiEvents.emit(UiEvents.PopBack)
                    }
                }

            }

            is NoteAddOrEditEvents.OnTitleChange -> _titleState.value = events.title
            is NoteAddOrEditEvents.OnNoteColorChange -> {
                _noteColor.value = events.color
            }
        }
    }

    fun updateNoteId(noteId: Int) {
        _noteId.value = noteId
        getNoteById(_noteId.value)
    }

    fun getNoteById(noteId: Int) {
        if (noteId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getNoteById(noteId).collect {
                    it?.let {
                        note = it
                        _titleState.value = it.noteTitle
                        _contentState.value = it.noteContent
                        _noteColor.value = Color(it.noteColor)
                    }
                }
            }
        }
    }


}