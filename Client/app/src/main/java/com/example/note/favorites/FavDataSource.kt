package com.example.note.favorites

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.note.RetrofitService
import com.example.note.data.Note
import java.lang.Exception

class FavDataSource(private val api: RetrofitService): PagingSource<Int, Note>(){
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
            var nextPageNumber = params.key?:1

            when (params) {
                is LoadParams.Refresh -> {
                    nextPageNumber = 1
                }
                else -> {}
            }

            val response = api.getFavList(nextPageNumber)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(response.count()==0) null else nextPageNumber + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}