package com.larrex.panorama.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result> {

        Log.d(TAG, "doGoogleAuth: ")

        return repository.doGoogleAuth(authCredential)

    }

    fun authState(): Flow<Boolean> {

        return repository.isAuthenticated()
    }

    fun getUserDetails(): Flow<User?> {

        return repository.getUserDetails()

    }

    fun getTrending(): Flow<Movies?> {

        return repository.getTrending()

    }

    fun getCategory(): Flow<Category?> {

        return repository.getCategory()

    }

    fun getMoviesWithGenres(id : String): Flow<Movies?> {

        return repository.getMoviesWithGenres(id)

    }

}