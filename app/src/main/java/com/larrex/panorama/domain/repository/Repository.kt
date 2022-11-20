package com.larrex.panorama.domain.repository

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.NetworkResult
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.FavouriteMovie
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.Search
import com.larrex.panorama.domain.retrofit.model.moviedetails.Credits
import com.larrex.panorama.domain.retrofit.model.moviedetails.CreditsTv
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun isAuthenticated(): Flow<Boolean>

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result>

    fun getUserDetails(): Flow<User?>
    fun getTrending(): Flow<NetworkResult<Movies?>>
    fun getCategory(): Flow<NetworkResult<Category?>>
    fun getCategoryTv(): Flow<NetworkResult<Category?>>
    fun getMoviesWithGenres(id: String,page: String): Flow<NetworkResult<Movies?>>
    fun getTvWithGenres(id: String,page: String): Flow<NetworkResult<Movies?>>
    fun getTvWithNetwork(id: String, page: String): Flow<NetworkResult<Movies?>>
    fun getMovieDetails(id: String): Flow<NetworkResult<MovieDetails?>>
    fun getMovieCredits(id: String): Flow<NetworkResult<Credits?>>
    fun getTvDetails(id: String): Flow<NetworkResult<TvDetails?>>
    fun getTvCredits(id: String): Flow<NetworkResult<CreditsTv?>>
    fun addToFavouriteMovies(favouriteMovie: FavouriteMovie)
    fun updateProfile(name : String?,uri: Uri?)
    fun search(keyword: String, page: String): Flow<NetworkResult<Search?>>

}