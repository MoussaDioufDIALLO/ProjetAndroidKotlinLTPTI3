package data.repositery

import domain.repository.Noterepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl (
    private val  dao: NoteDao
        ): Noterepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}