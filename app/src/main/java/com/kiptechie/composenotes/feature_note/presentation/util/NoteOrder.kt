package com.kiptechie.composenotes.feature_note.presentation.util

sealed class NoteOrder{
    class  Title(orderType: OrderType):NoteOrder(OrderType)
    class  Date(orderType: OrderType):NoteOrder(OrderType)
    class  color(orderType: OrderType):NoteOrder(OrderType)

}
