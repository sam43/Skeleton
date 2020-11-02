package com.example.skeleton.ui.characterdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.skeleton.data.entities.CharacterItem
import com.example.skeleton.data.repository.CharacterRepository
import com.example.skeleton.utils.Resource

class CharacterDetailViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { id ->
        repository.getCharacter(id)
    }
    val character: LiveData<Resource<CharacterItem>> = _character


    fun start(id: Int) {
        _id.value = id
    }

}
