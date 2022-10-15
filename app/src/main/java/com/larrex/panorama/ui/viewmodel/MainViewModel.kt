package com.larrex.panorama.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    fun doGoogleAuth(authCredential: AuthCredential?)  {

        Log.d(TAG, "doGoogleAuth: ")

         repository.doGoogleAuth(authCredential)

    }

    fun authState(result: Result) : Flow<Result> {

        return repository.isAuthenticated(result)
    }

}