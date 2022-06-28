package com.example.note

import com.example.note.data.Note
import com.example.note.data.OptRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @GET("note/list")
    suspend fun getNotes(@Query("page") page: Int): List<Note>

    @GET("note/list/fav")
    suspend fun getFavList(@Query("page") page: Int): List<Note>

    @GET("note/{id}")
    suspend fun getOneNote(@Path("id") id: String): List<Note>

    @GET("note/list/options")
    suspend fun getNotesByRange(
        @Query("page") page: Int,
        @Query("start") start: String,
        @Query("end") end: String
    ): List<Note>

    @POST("note")
    suspend fun insertNote(@Body note: Note)

    @DELETE("note/{id}")
    suspend fun deleteNote(@Path("id") id: String)

    @PATCH("note/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body note: Note)

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService{
            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.7:9898/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}