package ir.fnndev.notesapp.ui.screen.note_list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.fnndev.notesapp.ui.theme.cornerDp
import ir.fnndev.notesapp.ui.theme.smallDp
import ir.fnndev.notesapp.ui.theme.smallestDp
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
fun SearchBar(searchText: String, onChangeValue: (String) -> Unit) {

    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(cornerDp))
                .border(
                    width = smallestDp,
                    color = Color.Black,
                    shape = RoundedCornerShape(cornerDp)
                ),
            value = searchText,
            onValueChange = onChangeValue,
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            }, leadingIcon = {
                IconButton(onClick = {
                    if (searchText.isEmpty()) {
                        focusManager.clearFocus()
                    } else {
                        onChangeValue("")
                    }
                }) {
                    if (searchText.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close, "Close"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, "Back"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )
    }
}