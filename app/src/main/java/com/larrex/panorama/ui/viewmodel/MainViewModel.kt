package com.larrex.panorama.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Status
import com.larrex.panorama.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    fun doGoogleAuth(authCredential: AuthCredential): Flow<Status> {

        return repository.doGoogleAuth(authCredential)

    }

}