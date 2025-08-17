package ir.fnndev.notesapp.ui.screen.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.fnndev.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val notesList = repository.getNotes()

    fun onEvents(events: NoteListEvents) {
        when (events) {
            is NoteListEvents.OnDeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteNote(events.note)
                }
            }

            else -> Unit
        }
    }
}