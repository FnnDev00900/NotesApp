package ir.fnndev.notesapp.ui.screen.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.fnndev.notesapp.data.entity.Note
import ir.fnndev.notesapp.data.repository.NoteRepository
import ir.fnndev.notesapp.utils.Screens
import ir.fnndev.notesapp.utils.UiEvents
import ir.fnndev.notesapp.utils.UiEvents.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filterList: StateFlow<List<Note>> =
        searchText
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    repository.getNotes()
                } else {
                    repository.getFilteredNotes(query)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

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
                        NavigateTo(Screens.NoteAddOrEditScreen.withArgs(events.note.noteId.toString()))
                    )
                }
            }

            is NoteListEvents.OnSearchTextChange -> {
                _searchText.value = events.searchText
            }
        }
    }
}