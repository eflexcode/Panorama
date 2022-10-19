package com.larrex.panorama.domain.retrofit

import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Trending
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("trending/all/day?api_key="+Util.API_KEY)
    fun getTrending() : Call<Trending>

    @GET("genre/movie/list?api_key="+Util.API_KEY+"&language=en-US")
    fun getCategory() : Call<Category>

}