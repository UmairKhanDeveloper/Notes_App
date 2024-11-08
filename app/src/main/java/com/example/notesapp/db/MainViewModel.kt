package com.example.notesapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    suspend fun Insert(note: Note) {
        viewModelScope.launch {
            repository.Insert(note)
        }
    }

    suspend fun Update(note: Note) {
        viewModelScope.launch {
            repository.Update(note)
        }
    }

    suspend fun Delete(note: Note) {
        repository.Delete(note)
    }

}