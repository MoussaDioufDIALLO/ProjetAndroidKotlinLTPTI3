package use_case

import data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import util.NoteOrder
import util.OrderType

class GetNotes(
    private  val repository: NoteRepository
) {
    operator fun invoke(noteOrder: NoteOrder= NoteOrder.Date(oderType.Descending)
    ):Flow<List<Note>>{
        return repository.getNotes().map { notes -> }
        when (noteOrder.orderType){
            is OrderType.Ascending -> {
                when (noteOder){
                    is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    is NoteOrder.Date ->  notes.sortedBy { it.timestamp }
                    is NoteOrder.Color -> notes.sortedBy { it.color }

                }
            }
            is OrderType.Descending -> {
                when (noteOder){
                    is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    is NoteOrder.Date ->  notes.sortedByDescending  { it.timestamp }
                    is NoteOrder.Color -> notes.sortedByDescending  { it.color }


                }
            }
        }
    }


