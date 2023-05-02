package com.kiptechie.composenotes.feature_note.presentation.notes

import com.kiptechie.composenotes.feature_note.data.domain.model.Note
import com.kiptechie.composenotes.feature_note.data.domain.util.NoteOrder
import com.kiptechie.composenotes.feature_note.data.domain.util.OrderType

data class NotesState(
    //Liste des notes actuelles
    val notes: List<Note> = emptyList(),
    //Paramétre de tri par défaut
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    //Indique si la section de tri est visible ou non
    val isOrderSectionVisible: Boolean = false
)
