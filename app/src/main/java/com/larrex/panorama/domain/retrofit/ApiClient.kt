package com.larrex.panorama.domain.retrofit

import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("trending/all/day?api_key="+Util.API_KEY)
    fun getTrending() : Call<Movies>

    @GET("genre/movie/list?api_key="+Util.API_KEY+"&language=en-US")
    fun getCategory() : Call<Category>

    @GET("discover/movie?api_key="+Util.API_KEY)
    fun getMoviesWithGenres(@Query("with_genres")  id : String) : Call<Movies>

}