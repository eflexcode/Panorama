package com.larrex.panorama.domain.repository

import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun isAuthenticated(): Flow<Boolean>

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result>

    fun getUserDetails(): Flow<User?>
    fun getTrending(): Flow<Movies?>
    fun getCategory(): Flow<Category?>
    fun getMoviesWithGenres(id: String): Flow<Movies?>

}