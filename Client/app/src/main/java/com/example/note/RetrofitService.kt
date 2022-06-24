package com.example.note

import com.example.note.data.Note
import com.example.note.data.OptRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @GET("list")
    suspend fun getNotes(@Query("page") page: Int): List<Note>

    @GET("list/options")
    suspend fun getNotesByRange(
        @Query("page") page: Int,
        @Query("start") start: String,
        @Query("end") end: String
    ): List<Note>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService{
            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.6:3000/note/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}