package ir.fnndev.notesapp.utils

sealed class UiEvents {
    data class NavigateTo(val route: String): UiEvents()
    data class ShowSnackBar(val message: String,val actionLabel: String? = null): UiEvents()
    data object PopBack: UiEvents()
}