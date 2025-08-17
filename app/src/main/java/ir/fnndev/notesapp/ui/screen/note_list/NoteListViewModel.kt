package ir.fnndev.notesapp.ui.screen.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.fnndev.notesapp.data.repository.NoteRepository
import ir.fnndev.notesapp.utils.Screens
import ir.fnndev.notesapp.utils.UiEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    val notesList = repository.getNotes()

    fun onEvents(events: NoteListEvents) {
        when (events) {
            is NoteListEvents.OnDeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteNote(events.note)
                }
            }

            is NoteListEvents.OnEditNote -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiEvents.emit(
                        UiEvents.NavigateTo(Screens.NoteAddOrEditScreen.withArgs(events.note.noteId.toString()))
                    )
                }
            }
        }
    }
}