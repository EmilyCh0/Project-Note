package com.example.note.data

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("_id") val id: String? = null,
    val timestamp: String? = null,
    val title: String? = null,
    val content: String? = null,
    val fav: Boolean = false
)
