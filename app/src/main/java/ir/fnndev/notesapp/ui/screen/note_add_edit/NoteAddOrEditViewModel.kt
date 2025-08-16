package ir.fnndev.notesapp.ui.screen.note_add_edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.fnndev.notesapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteAddOrEditViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {
    private val _titleState = MutableStateFlow("")
    val titleState = _titleState.asStateFlow()

    private val _contentState = MutableStateFlow("")
    val contentState = _contentState.asStateFlow()

    fun updateTitleState(newValue: String) {
        _titleState.value = newValue
    }

    fun updateContentState(newValue: String) {
        _contentState.value = newValue
    }
}