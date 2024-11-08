package com.example.notesapp.db

import androidx.lifecycle.LiveData

class Repository(private val noteDataBase: NoteDataBase) {

    fun getAllNotes(): LiveData<List<Note>> = noteDataBase.getDao().getAllNotes()

    suspend fun Insert(note: Note) {
        return noteDataBase.getDao().Insert(note)
    }

    suspend fun Update(note: Note) {
        noteDataBase.getDao().Update(note)
    }

    suspend fun Delete(note: Note) {
        noteDataBase.getDao().Delete(note)
    }
}