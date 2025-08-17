package ir.fnndev.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.fnndev.notesapp.ui.screen.note_add_edit.NoteAddOrEditScreen
import ir.fnndev.notesapp.ui.screen.note_list.NoteListScreen
import ir.fnndev.notesapp.utils.Screens

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.NoteListScreen.route) {
        composable(route = Screens.NoteListScreen.route) {
            NoteListScreen(navController)
        }
        composable(
            route = Screens.NoteAddOrEditScreen.route + "/{noteId}", arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val noteId = it.arguments?.getInt("noteId")
            NoteAddOrEditScreen(noteId = noteId!!,navController=navController)
        }
    }
}