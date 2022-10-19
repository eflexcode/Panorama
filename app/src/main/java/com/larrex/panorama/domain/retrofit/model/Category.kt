package com.larrex.panorama.domain.retrofit.model

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("genres" ) var genres : ArrayList<Genres> = arrayListOf()
)
