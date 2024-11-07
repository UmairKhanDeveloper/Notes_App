package com.example.notesapp.db

import androidx.lifecycle.LiveData

class Repository(private val noteDataBase: NoteDataBase) {
    fun getAllNotes(): LiveData<List<Note>> = noteDataBase.getDao().getAllNotes()

    suspend fun Insert(note: Note) {
        return noteDataBase.getDao().Insert(note)
    }
}