package com.larrex.panorama.domain.repository

import com.google.firebase.auth.AuthCredential
import com.larrex.panorama.core.Status
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun doGoogleAuth(authCredential: AuthCredential)  : Flow<Status>

}