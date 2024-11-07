package com.example.notesapp.db

import java.lang.Error

sealed class ResultState<out T> {
    object Loading:ResultState<Note>()
    data class Succses<T>(val succses: T):ResultState<T>()
    data class Error(val error: Throwable):ResultState<Note>()
}