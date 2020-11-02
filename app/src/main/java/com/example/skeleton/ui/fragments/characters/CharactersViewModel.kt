package com.example.skeleton.ui.fragments.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.skeleton.base.BaseViewModel
import com.example.skeleton.data.repository.CharacterRepository

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : BaseViewModel() {

    val characters = repository.getCharacters()
}
