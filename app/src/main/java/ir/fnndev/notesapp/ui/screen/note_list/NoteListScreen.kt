package ir.fnndev.notesapp.ui.screen.note_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.utils.Screens
import ir.fnndev.notesapp.utils.UiEvents

@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val listNotes = viewModel.filterList.collectAsState()
    val searchedText = viewModel.searchText.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.NavigateTo -> {
                    navController.navigate(event.route)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(smallDp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.NoteAddOrEditScreen.withArgs("-1"))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, "Add New")
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + smallDp,
                    bottom = innerPadding.calculateBottomPadding() + smallDp
                )
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchedText.value,
                    onValueChange = {
                        viewModel.onEvents(NoteListEvents.OnSearchTextChange(it))
                    }
                )
            }
            items(listNotes.value) { note ->
                NoteListItem(
                    note,
                    onEvent = viewModel::onEvents
                )
            }
        }
    }
}