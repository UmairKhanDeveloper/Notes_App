package com.example.notesapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val allNotes: LiveData<List<Note>> = repository.getAllNotes()

    suspend fun Insert(note: Note) {
        viewModelScope.launch {
            repository.Insert(note)
        }
    }

}