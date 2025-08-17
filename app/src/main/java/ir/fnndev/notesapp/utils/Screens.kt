package ir.fnndev.notesapp.utils

sealed class Screens(val route: String) {
    data object NoteListScreen : Screens("note_list")
    data object NoteAddOrEditScreen : Screens("note_add_edit")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}