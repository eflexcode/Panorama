package com.larrex.panorama.domain.retrofit

import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Trending
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("trending/all/day?api_key="+Util.API_KEY)
    fun getTrending() : Call<Trending>

}