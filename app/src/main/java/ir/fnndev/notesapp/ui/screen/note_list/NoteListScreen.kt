package ir.fnndev.notesapp.ui.screen.note_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.fnndev.notesapp.ui.theme.cornerDp
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.utils.Screens
import ir.fnndev.notesapp.utils.UiEvents
import kotlinx.coroutines.launch

@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val listNotes = viewModel.filterList.collectAsState()
    val searchedText = viewModel.searchText.collectAsState()

    val snackBarState = remember { SnackbarHostState() }
    val snackBarScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.NavigateTo -> {
                    navController.navigate(event.route)
                }

                is UiEvents.ShowSnackBar -> {
                    snackBarScope.launch {
                        snackBarState.currentSnackbarData?.dismiss()
                        val result = snackBarState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvents(NoteListEvents.OnUndoDelete)
                        }
                    }
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
        },
        snackbarHost = {
            SnackbarHost(snackBarState)
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
                SearchBar(
                    searchText = searchedText.value,
                    onChangeValue = {
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

@Composable
fun SearchBar(searchText: String, onChangeValue: (String) -> Unit,modifier: Modifier= Modifier) {
    OutlinedTextField(
        modifier = modifier,
        value = searchText,
        onValueChange = onChangeValue
    )
}