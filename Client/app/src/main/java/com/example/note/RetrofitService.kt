package com.example.note

import com.example.note.data.Note
import com.example.note.data.OptRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET

interface RetrofitService {

    @GET("api/note")
    fun getNotes(@Body body: OptRequest): List<Note>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService{
            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}