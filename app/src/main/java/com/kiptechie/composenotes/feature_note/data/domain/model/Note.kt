package com.kiptechie.composenotes.feature_note.data.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kiptechie.composenotes.ui.theme.*

@Entity
data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    val imagePath: String,
    @PrimaryKey (autoGenerate = true)
    val primaryKey: Int = 0

) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
        const val NOTE_COLOR_EXTRA = "noteColor"
        const val NOTE_ID_EXTRA = "noteId"
        const val NO_NOTE_OR_COLOR_ID = -1;
    }
}

class InvalidNoteException(message: String) : Exception(message)
