package com.example.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(note: Note)

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Note>>


}