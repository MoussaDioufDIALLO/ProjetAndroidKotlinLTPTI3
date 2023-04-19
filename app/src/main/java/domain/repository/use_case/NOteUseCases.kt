package domain.repository.use_case

import use_case.GetNotes

class NOteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote

)
