package com.kiptechie.composenotes.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kiptechie.composenotes.feature_note.data.domain.model.Note
import com.kiptechie.composenotes.feature_note.presentation.notes.components.NoteItem
import com.kiptechie.composenotes.feature_note.presentation.notes.components.OrderSection
import com.kiptechie.composenotes.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    //Récupére le viewmodel de Hilt
    viewModel: NotesViewModel = hiltViewModel()
) {
    //Récupération l'éat actuel de la liste des note
    val state = viewModel.state.value
    //Création de l'état de scaffold
    val scaffoldState = rememberScaffoldState()
    //Création des scopes pour les coroutines
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            //Ajout du bouton pour ajouter une nouvelle note
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) { FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNotesScreen.route)
                },
                backgroundColor = Color(255, 175, 0),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }


            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )

        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(red = 255, green =175, blue = 0))
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Toutes les Notes",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 8.dp)
                )
                //Boutton pour afficher/cacher la section de tri
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.TextSnippet,
                        contentDescription = "Sort",
                        modifier = Modifier
                            .size(36.dp),
                    )

                }
                //Texte pour afficher trier
                Text(
                    text = "Trier",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    })
                )
            }
            //Boite pour afficher la ligne séparatrice
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .background(Color.Black)
                    .fillMaxWidth()
            )
            //Section de tri animé
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNotesScreen.route +
                                            "?${Note.NOTE_ID_EXTRA}=${note.id}&${Note.NOTE_COLOR_EXTRA}=${note.color}"
                                )
                            }
                            .border(BorderStroke(2.dp, Color.Black)),
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                //Afficher un snackbar pour confirmer la suppression de la note
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Annuler"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    //Restaurer la note si l'utilisateur annule la suppression
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }

        }
    }
}