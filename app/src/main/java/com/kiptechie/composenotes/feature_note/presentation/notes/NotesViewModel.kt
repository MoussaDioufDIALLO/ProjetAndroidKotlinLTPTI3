package com.kiptechie.composenotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiptechie.composenotes.feature_note.data.domain.model.Note
import com.kiptechie.composenotes.feature_note.data.domain.use_case.NoteUseCases
import com.kiptechie.composenotes.feature_note.data.domain.util.NoteOrder
import com.kiptechie.composenotes.feature_note.data.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    //Etat actuel de l'interface utilisateur du user
     private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
    //Note récemment supprimée
    private var recentlyDeletedNote: Note? = null
    //Récupération des notes en cours
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                //Lancer la coroutine pour la suppression de la note
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                //Enregistrement de la note récemment supprimée
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.Order -> {
                // Si l'ordre est inchangé ne rien faire
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                //Récupération des notes en fonction du nouveau ordre
                getNotes(event.noteOrder)
            }
            is NotesEvent.RestoreNote -> {
                //Ajoute la note récemment supprimée de la base de données
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            //Inverser la visibilité de la section de triage
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        //Annuler la tache de récupération en cours
        getNotesJob?.cancel()
        //Réinitiliser la tache à null
        getNotesJob = null
        //Créer une nouvelle tache pour récupérer les notes
        getNotesJob = noteUseCases.getNotes(noteOrder = noteOrder)
            .onEach { notes ->
                //Mettre à jour les notes et l'ordre de tri
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            //Lancer la tache dans le viewModel
            .launchIn(viewModelScope)
    }
}