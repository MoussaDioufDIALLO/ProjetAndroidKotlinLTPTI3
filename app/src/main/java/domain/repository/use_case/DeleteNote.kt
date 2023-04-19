package domain.repository.use_case

import domain.repository.Noterepository

class DeleteNote(
    private  val  repository : Noterepository
) {
    suspend operator fun invoke (note: Note){
        repository.deleteNote(note)
    }

}