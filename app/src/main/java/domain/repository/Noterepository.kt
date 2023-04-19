package domain.repository

interface Noterepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun  getNoteById(id: Int): Note?

    suspend fun insertNote(note : Note)

    suspend fun deleteNote(note: Note)


}