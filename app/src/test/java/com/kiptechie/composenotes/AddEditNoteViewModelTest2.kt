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


class AddEditNoteViewModelTest2 {
    @Test
    fun when_user_enters_note_content_content_state_should_be_updated() {
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
        val newContent = "New Note Content"
        val event = AddEditNoteEvent.EnteredContent(newContent)

        // When
        viewModel.onEvent(event)

        // Then
        val noteTitle = runBlocking { viewModel.noteTitle.value }
        val expectedNoteTitle =
            NoteTextFieldState(hint = "Entrer le titre...")
        assertEquals(expectedNoteTitle, noteTitle)

        val noteContent = runBlocking { viewModel.noteContent.value }
        val expectedNoteContent = NoteTextFieldState(text = newContent, hint = "Enter le contenu...", isHintVisible = true)
        assertEquals(expectedNoteContent, noteContent)
    }
}