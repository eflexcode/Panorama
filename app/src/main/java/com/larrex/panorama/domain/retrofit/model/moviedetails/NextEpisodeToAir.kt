package com.larrex.panorama.domain.retrofit.model.moviedetails

import com.google.gson.annotations.SerializedName

data class NextEpisodeToAir(
    @SerializedName("air_date") var airDate: String? = null,
    @SerializedName("episode_number") var episodeNumber: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("production_code") var productionCode: String? = null,
    @SerializedName("runtime") var runtime: Float? = null,
    @SerializedName("season_number") var seasonNumber: Float? = null,
    @SerializedName("show_id") var showId: Float? = null,
    @SerializedName("still_path") var stillPath: String? = null,
    @SerializedName("vote_average") var voteAverage: Float? = null,
    @SerializedName("vote_count") var voteCount: Float? = null
)
