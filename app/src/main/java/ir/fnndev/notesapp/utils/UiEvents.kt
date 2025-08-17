package ir.fnndev.notesapp.utils

sealed class UiEvents {
    data class NavigateTo(val route: String): UiEvents()
    data object PopBack: UiEvents()
}