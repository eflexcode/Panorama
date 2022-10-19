package com.larrex.panorama.domain.repository

import androidx.navigation.NavHostController
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Trending
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun isAuthenticated(): Flow<Boolean>

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result>

    fun getUserDetails() : Flow<User?>
    fun getTrending() : Flow<Trending?>
    fun getCategory() : Flow<Category?>

}