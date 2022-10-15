package com.larrex.panorama.di.repository

import android.app.Application
import android.util.Log
import androidx.navigation.NavHostController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.larrex.panorama.Util
import com.larrex.panorama.core.Status
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
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
    private val auth: FirebaseAuth
) : Repository {

    override fun isAuthenticated(result: Result): Flow<Result> {
        return flow<Result> {

            if (result.status != Status.NOTHING) {
                Log.d(TAG, "isAuthenticated: ")
                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun doGoogleAuth(
        authCredential: AuthCredential?,
    ) {

        Log.d(TAG, "doGoogleAuth: ")

        print("fffffffffffff")


//            trySend(Result(Status.LOADING, ""))
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

                                isAuthenticated(Result(Status.SUCCESS, "SUCCESS"))

//                                    trySend(Result(Status.SUCCESS, "SUCCESS"))

                            }.addOnFailureListener { error ->

                                error.message?.let {
                                    Result(
                                        Status.FAILURE,
                                        it
                                    )
                                }?.let { isAuthenticated(it) }


//                                    error.message?.let { Result(Status.FAILURE, it) }
//                                        ?.let { trySend(it) }

                            }

                        } else {
//                                trySend(Result(Status.SUCCESS, "SUCCESS"))

                            isAuthenticated(Result(Status.SUCCESS, "SUCCESS"))

                        }

                    } else {
//                            trySend(Result(Status.FAILURE, "Something went wrong"))
                        isAuthenticated(Result(Status.FAILURE, "Something went wrong"))


                    }

                })

//            awaitClose { }


//        }

    }


}

//    override fun doGoogleAuth(authCredential: AuthCredential): Flow<Status> {
//
//        return callbackFlow<Status> {
//
//            trySend()
//            Log.d(TAG, "doGoogleAuth: ")
//
//        }.flowOn(Dispatchers.IO)
//
//    }

