package com.kiptechie.composenotes.feature_note.data.domain.use_case

import com.kiptechie.composenotes.feature_note.data.domain.model.InvalidNoteException
import com.kiptechie.composenotes.feature_note.data.domain.model.Note
import com.kiptechie.composenotes.feature_note.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Le titre de la note ne peut pas être vide.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Le contenu de la note ne peut pas être vide.")
        }
        repository.insertNote(note = note)
    }
}