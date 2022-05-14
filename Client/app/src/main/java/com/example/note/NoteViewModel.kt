package com.example.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.note.data.OptRequest

class NoteViewModel(private val api: RetrofitService, private val opt: OptRequest): ViewModel() {

    var items = Pager(config = PagingConfig(pageSize = 5), pagingSourceFactory = {
        NoteDataSource(api, opt)
    }).flow.cachedIn(viewModelScope)
}