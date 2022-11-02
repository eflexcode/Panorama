package com.larrex.panorama.domain.repository

import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.moviedetails.Credits
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun isAuthenticated(): Flow<Boolean>

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result>

    fun getUserDetails(): Flow<User?>
    fun getTrending(): Flow<Movies?>
    fun getCategory(): Flow<Category?>
    fun getCategoryTv(): Flow<Category?>
    fun getMoviesWithGenres(id: String): Flow<Movies?>
    fun getTvWithGenres(id: String): Flow<Movies?>
    fun getTvWithNetwork(id: String,page :String): Flow<Movies?>
    fun getMovieDetails(id: String): Flow<MovieDetails?>
    fun getMovieCredits(id: String): Flow<Credits?>
     fun getTvDetails(id: String): Flow<TvDetails?>

}