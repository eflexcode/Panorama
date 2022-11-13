package com.larrex.panorama.di.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.larrex.panorama.Util
import com.larrex.panorama.core.Result
import com.larrex.panorama.core.Status
import com.larrex.panorama.domain.model.FavouriteMovie
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
import com.larrex.panorama.domain.retrofit.ApiClient
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.Search
import com.larrex.panorama.domain.retrofit.model.moviedetails.Credits
import com.larrex.panorama.domain.retrofit.model.moviedetails.CreditsTv
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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


            if (authCredential != null)

                auth.signInWithCredential(authCredential)
                    .addOnCompleteListener(OnCompleteListener { authResult ->

                        if (authResult.isSuccessful) {

                            val newUser = authResult.result.additionalUserInfo?.isNewUser

                            if (newUser == true) {

//                                val firebaseFirestore = FirebaseFirestore.getInstance()

                                val collectionReference =
                                    firestore.collection(Util.USER_COLLECTION)

                                val documentReference =
                                    collectionReference.document(auth.uid!!)

                                val id: String = auth.uid!!
                                val name: String = authResult.result.user?.displayName!!
                                val imageUrl: String = authResult.result.user?.photoUrl.toString()
                                val emial: String = authResult.result.user!!.email.toString()

                                val user = User(id, emial, name, imageUrl)

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

    override fun getTrending(): Flow<Movies?> {

        return flow<Movies?> {

            val trending = apiClient.getTrending().execute()

            if (trending.isSuccessful) {

                emit(trending.body())

            } else {
                val trending1 = apiClient.getTrending().execute()

                if (trending1.isSuccessful) {

                    emit(trending1.body())

                }
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

    override fun getCategoryTv(): Flow<Category?> {
        return flow<Category?> {

            val category = apiClient.getCategoryTv().execute()

            if (category.isSuccessful) {

                emit(category.body())

            }


        }.flowOn(Dispatchers.IO)
    }

    override fun getMoviesWithGenres(id: String, page: String): Flow<Movies?> {

        return flow<Movies?> {

            val movies = apiClient.getMoviesWithGenres(id, page).execute()

            if (movies.isSuccessful) {

                emit(movies.body())

            }

        }.flowOn(Dispatchers.IO)

    }

    override fun getTvWithGenres(id: String, page: String): Flow<Movies?> {

        return flow<Movies?> {

            val tv = apiClient.getTvWithGenres(id, page).execute()

            if (tv.isSuccessful) {

                emit(tv.body())

            }

        }.flowOn(Dispatchers.IO)


    }

    override fun getTvWithNetwork(id: String, page: String): Flow<Movies?> {
        return flow<Movies?> {

            val tv = apiClient.getTvWithNetwork(id, page).execute()

            if (tv.isSuccessful) {

                emit(tv.body())

            }

        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetails(id: String): Flow<MovieDetails?> {

        return flow<MovieDetails?> {

            val movie = apiClient.getMovieDetails(id).execute()

            if (movie.isSuccessful) {

                emit(movie.body())

            }

        }.flowOn(Dispatchers.IO)

    }

    override fun getMovieCredits(id: String): Flow<Credits?> {

        return flow<Credits?> {

            val credits = apiClient.getMovieCredits(id).execute()

            if (credits.isSuccessful) {

                emit(credits.body())

            }

        }.flowOn(Dispatchers.IO)

    }

    override fun getTvDetails(id: String): Flow<TvDetails?> {

        return flow<TvDetails?> {

            val tvDetails = apiClient.getTvDetails(id).execute()

            if (tvDetails.isSuccessful) {

                emit(tvDetails.body())

            }

        }.flowOn(Dispatchers.IO)

    }

    override fun getTvCredits(id: String): Flow<CreditsTv?> {
        return flow<CreditsTv?> {

            val credits = apiClient.getTvCredits(id).execute()

            if (credits.isSuccessful) {

                emit(credits.body())

            }

        }.flowOn(Dispatchers.IO)
    }

    override fun addToFavouriteMovies(favouriteMovie: FavouriteMovie) {

        val documentReference = firestore.collection("Favourites")
            .document(auth.currentUser?.uid.toString())
            .collection("MyFavourites")
            .document(favouriteMovie.id.toString())

        Log.d(TAG, "addToFavouriteMovies: " + favouriteMovie.firebaseId.toString())

        documentReference.set(favouriteMovie).addOnSuccessListener {

        }


    }

    override fun updateProfile(name: String?, uri: Uri?) {

        if (uri == null) {
            val firebaseFirestore = FirebaseFirestore.getInstance()

            val collectionReference = firebaseFirestore.collection("Users")

            val documentReference = collectionReference.document(
             auth.uid!!
            )

            val map: MutableMap<String, Any> = HashMap()
            map.put("name",name!!)

            documentReference.update(map)

        } else {

            val firebaseStorage = FirebaseStorage.getInstance()

            val storageReference = firebaseStorage.getReference("profilePictures")
            val fileReference = storageReference.child(System.currentTimeMillis().toString() + "")

            val uploadTask = fileReference.putFile(uri)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                fileReference.downloadUrl
            }.addOnCompleteListener { task ->
                val firebaseFirestore = FirebaseFirestore.getInstance()
                val collectionReference =
                    firebaseFirestore.collection("Users")
                val documentReference = collectionReference.document(
                    FirebaseAuth.getInstance().uid!!
                )
                val map: MutableMap<String, Any> =
                    java.util.HashMap()
                map.put("name",  name!!)
                map.put("imageUrl",  task.result.toString())
                documentReference.update(map)
            }.addOnFailureListener {

            }


        }

    }

    override fun search(keyword: String, page: String): Flow<Search?> {

        return flow<Search?> {

            val search = apiClient.search(keyword,page).execute()

            if (search.isSuccessful) {

                emit(search.body())

            }

        }.flowOn(Dispatchers.IO)

    }

}