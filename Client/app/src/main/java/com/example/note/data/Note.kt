package com.example.note.data

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("_id") val id: String? = null,
    var timestamp: String? = null,
    var title: String? = null,
    var content: String? = null,
    var fav: Boolean = false
)
