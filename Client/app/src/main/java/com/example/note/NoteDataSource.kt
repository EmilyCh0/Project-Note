package com.example.note

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.note.data.Note
import com.example.note.data.OptRequest
import java.lang.Exception

class NoteDataSource(private val api: RetrofitService): PagingSource<Int, Note>() {
    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
            Log.d("PAGER", "refresh")
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        return try {
            Log.d("PAGER", "load")
            val nextPageNumber = params.key?:1
            val response = api.getNotes(nextPageNumber)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}