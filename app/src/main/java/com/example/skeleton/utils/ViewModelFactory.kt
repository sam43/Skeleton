package com.example.skeleton.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Coroutines and ktx already providing a factory to initialize a viewmodel lazy,
 * No need of this custom viewmodel factory class anymore
 * */

class ViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}