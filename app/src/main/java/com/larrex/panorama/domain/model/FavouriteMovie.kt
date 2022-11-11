package com.larrex.panorama.domain.model

import com.google.gson.annotations.SerializedName

data class FavouriteMovie(
    var tv: Boolean? = null,
    var firebaseId: String? = null,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var id: Int? = null,
    var title: String? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var posterPath: String? = null,
    var mediaType: String? = null,
    var popularity: Double? = null,
    var releaseDate: String? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null
)
