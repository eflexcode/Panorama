package com.larrex.panorama.domain.repository

import androidx.navigation.NavHostController
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun isAuthenticated(result: Result): Flow<Result>

    fun doGoogleAuth(authCredential: AuthCredential?)

}