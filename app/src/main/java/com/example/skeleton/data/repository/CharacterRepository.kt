package com.example.skeleton.data.repository

import com.example.skeleton.data.local.CharacterDao
import com.example.skeleton.data.remote.CharacterRemoteDataSource
import com.example.skeleton.utils.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
        private val remoteDataSource: CharacterRemoteDataSource,
        private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = { localDataSource.getAllCharacters() },
        networkCall = { remoteDataSource.getCharacters() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}