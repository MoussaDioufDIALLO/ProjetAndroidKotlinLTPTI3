package com.kiptechie.composenotes

import androidx.lifecycle.SavedStateHandle
import com.kiptechie.composenotes.feature_note.data.domain.use_case.*
import com.kiptechie.composenotes.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.kiptechie.composenotes.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.kiptechie.composenotes.feature_note.presentation.add_edit_note.NoteTextFieldState
import com.kiptechie.composenotes.feature_note.repository.NoteRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AddEditNoteViewModelTest {

    @Test
    fun when_user_enters_note_title_title_state_should_be_updated() {
        // Given
        val noteRepository = mockk<NoteRepository>()
        val noteUseCases = NoteUseCases(
            GetNotes(noteRepository),
            DeleteNote(noteRepository),
            AddNote(noteRepository),
            GetNote(noteRepository)
        )
        val savedStateHandle = mockk<SavedStateHandle>()
        every { savedStateHandle.get<String>(any()) } returns null
        val viewModel = AddEditNoteViewModel(noteUseCases, savedStateHandle)
        val newTitle = "New Note Title"
        val event = AddEditNoteEvent.EnteredTitle(newTitle)

        // When
        viewModel.onEvent(event)

        // Then
        val noteTitle = runBlocking { viewModel.noteTitle.value }
        val expectedNoteTitle =
            NoteTextFieldState(text = newTitle, hint = "Entrer le titre...", isHintVisible = true)
        assertEquals(expectedNoteTitle, noteTitle)

        val noteContent = runBlocking { viewModel.noteContent.value }
        val expectedNoteContent = NoteTextFieldState(hint =  "Enter le contenu...")
        assertEquals(expectedNoteContent, noteContent)
    }
}
