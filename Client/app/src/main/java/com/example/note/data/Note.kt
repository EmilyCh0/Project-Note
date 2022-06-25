package com.example.note.data

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("_id") val id: String?,
    val timestamp: String?,
    val title: String?,
    val content: String?,
    val fav: Boolean = false
)
