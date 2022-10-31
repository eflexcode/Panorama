package com.larrex.panorama.domain.retrofit.model.moviedetails

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cast") var cast: ArrayList<Cast> = arrayListOf(),
    @SerializedName("crew") var crew: ArrayList<Crew> = arrayListOf()
)
