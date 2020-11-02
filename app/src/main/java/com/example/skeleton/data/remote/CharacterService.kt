package com.example.skeleton.data.remote

import com.example.skeleton.data.entities.CharacterItem
import com.example.skeleton.data.entities.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters() : Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterItem>
}