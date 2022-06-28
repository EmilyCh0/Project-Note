package com.example.note.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.RetrofitService

class FavViewModelFactory(private val api: RetrofitService): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavViewModel(api) as T
    }
}