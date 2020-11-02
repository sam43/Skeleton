package com.example.skeleton.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skeleton.data.entities.CharacterItem

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<CharacterItem>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<CharacterItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterItems: List<CharacterItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterItem: CharacterItem)


}