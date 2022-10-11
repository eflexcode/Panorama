package com.larrex.panorama.di.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.larrex.panorama.Util
import com.larrex.panorama.core.Status
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override fun doGoogleAuth(authCredential: AuthCredential) = callbackFlow<Status> {

        trySend(Status.LOADING)

        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(
            OnCompleteListener { authResult ->

                if (authResult.isSuccessful) {

                    val newUser = authResult.result.additionalUserInfo?.isNewUser

                    if (newUser == true) {

                        trySend(Status.SUCCESS)

                    } else {

                        val firebaseFirestore = FirebaseFirestore.getInstance()

                        val collectionReference = firebaseFirestore.collection(Util.USER_COLLECTION)

                        val documentReference = collectionReference.document(firebaseAuth.uid!!)

                        val id: String = firebaseAuth.uid!!
                        val name: String = authResult.result.user?.displayName!!
                        val imageUrl: String = authResult.result.user?.photoUrl.toString()

                        val user = User(id, name, imageUrl)

                        documentReference.set(user).addOnSuccessListener {

                            trySend(Status.SUCCESS)

                        }.addOnFailureListener {

                            trySend(Status.FAILURE)

                        }

                    }

                } else {

                    trySend(Status.FAILURE)

                }

            })

    }

}