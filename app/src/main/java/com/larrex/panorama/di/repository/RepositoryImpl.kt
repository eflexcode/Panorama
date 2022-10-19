package com.larrex.panorama.di.repository

import android.app.Application
import android.util.Log
import androidx.navigation.NavHostController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.larrex.panorama.Util
import com.larrex.panorama.core.Status
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
import com.larrex.panorama.domain.retrofit.ApiClient
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Trending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RepositoryImpl"

class RepositoryImpl @Inject constructor(
    private var application: Application,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val apiClient: ApiClient
) : Repository {

    override fun isAuthenticated(): Flow<Boolean> {

        return flow<Boolean> {


            if (auth.currentUser != null) {
                emit(true)
                Log.d(TAG, "isAuthenticated: yes")
            } else {
                emit(false)
                Log.d(TAG, "isAuthenticated: no")
            }

        }.flowOn(Dispatchers.IO)

    }

    override suspend fun doGoogleAuth(
        authCredential: AuthCredential?,
    ): Flow<Result> {

        Log.d(TAG, "doGoogleAuth: ")

        print("fffffffffffff")

        return callbackFlow<Result> {

            trySend(Result(Status.LOADING, ""))

            val firebaseAuth = FirebaseAuth.getInstance()

            if (authCredential != null)

                firebaseAuth.signInWithCredential(authCredential)
                    .addOnCompleteListener(OnCompleteListener { authResult ->

                        if (authResult.isSuccessful) {

                            val newUser = authResult.result.additionalUserInfo?.isNewUser

                            if (newUser == true) {

                                val firebaseFirestore = FirebaseFirestore.getInstance()

                                val collectionReference =
                                    firebaseFirestore.collection(Util.USER_COLLECTION)

                                val documentReference =
                                    collectionReference.document(firebaseAuth.uid!!)

                                val id: String = firebaseAuth.uid!!
                                val name: String = authResult.result.user?.displayName!!
                                val imageUrl: String = authResult.result.user?.photoUrl.toString()

                                val user = User(id, name, imageUrl)

                                documentReference.set(user).addOnSuccessListener {
                                    trySend(Result(Status.SUCCESS, ""))
                                }.addOnFailureListener { error ->
                                    trySend(Result(Status.FAILURE, error.message))
                                }

                            } else {
                                trySend(Result(Status.SUCCESS, ""))
                            }

                        } else {
                            trySend(Result(Status.FAILURE, "Something went wrong"))
                        }

                    })

            awaitClose { }

        }.flowOn(Dispatchers.IO)
    }

    override fun getUserDetails(): Flow<User?> {


        return callbackFlow<User?> {

            auth.currentUser?.uid?.let {
                firestore.collection(Util.USER_COLLECTION).document(it).addSnapshotListener(
                    EventListener { value, error ->

                        val user: User? = value?.toObject(User::class.java)
                        Log.d(TAG, "getUserDetails: ${user?.imageUrl}")
                        trySend(user)

                    })
            }

            awaitClose { }

        }.flowOn(Dispatchers.IO)
    }

    override fun getTrending(): Flow<Trending?> {

        return flow<Trending?> {

            val trending = apiClient.getTrending().execute()

            if (trending.isSuccessful) {

                emit(trending.body())

            }


        }.flowOn(Dispatchers.IO)

    }

    override fun getCategory(): Flow<Category?> {
        return flow<Category?> {

            val category = apiClient.getCategory().execute()

            if (category.isSuccessful) {

                emit(category.body())

            }


        }.flowOn(Dispatchers.IO)
    }


}