package com.larrex.panorama.domain.retrofit

import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.moviedetails.Credits
import com.larrex.panorama.domain.retrofit.model.moviedetails.CreditsTv
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("trending/all/day?api_key=" + Util.API_KEY)
    fun getTrending(): Call<Movies>

    @GET("genre/movie/list?api_key=" + Util.API_KEY + "&language=en-US")
    fun getCategory(): Call<Category>

    @GET("genre/tv/list?api_key=" + Util.API_KEY + "&language=en-US")
    fun getCategoryTv(): Call<Category>

    @GET("discover/movie?api_key=" + Util.API_KEY)
    fun getMoviesWithGenres(
        @Query("with_genres") id: String,
        @Query("page") page: String
    ): Call<Movies>

    @GET("discover/tv?api_key=" + Util.API_KEY + "&sort_by=popularity.desc")
    fun getTvWithGenres(
        @Query("with_genres") id: String,
        @Query("page") page: String
    ): Call<Movies>

    @GET("discover/tv?api_key=" + Util.API_KEY)
    fun getTvWithNetwork(
        @Query("with_networks") id: String,
        @Query("page") page: String
    ): Call<Movies>

    @GET("movie/{movie_id}?api_key=" + Util.API_KEY + "&language=en-US")
    fun getMovieDetails(@Path("movie_id") id: String): Call<MovieDetails>

    @GET("tv/{tv_id}?api_key=" + Util.API_KEY + "&language=en-US")
    fun getTvDetails(@Path("tv_id") id: String): Call<TvDetails>

    @GET("movie/{movie_id}/credits?api_key=" + Util.API_KEY + "&language=en-US")
    fun getMovieCredits(@Path("movie_id") id: String): Call<Credits>

    @GET("tv/{tv_id}/credits?api_key=" + Util.API_KEY + "&language=en-US")
    fun getTvCredits(@Path("tv_id") id: String): Call<CreditsTv>

}