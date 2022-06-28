package com.example.note.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.note.RetrofitService

class FavViewModel(private val api: RetrofitService): ViewModel() {
    var items = Pager(config = PagingConfig(pageSize = 5), pagingSourceFactory = {
        FavDataSource(api)
    }).flow.cachedIn(viewModelScope)
}