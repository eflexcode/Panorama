package com.larrex.panorama.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.larrex.panorama.core.NetworkResult
import com.larrex.panorama.core.Result
import com.larrex.panorama.domain.model.FavouriteMovie
import com.larrex.panorama.domain.model.User
import com.larrex.panorama.domain.repository.Repository
import com.larrex.panorama.domain.retrofit.model.*
import com.larrex.panorama.domain.retrofit.model.moviedetails.Credits
import com.larrex.panorama.domain.retrofit.model.moviedetails.CreditsTv
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository: Repository,
    private val savedStateHandle: SavedStateHandle,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    suspend fun doGoogleAuth(authCredential: AuthCredential?): Flow<Result> {

        Log.d(TAG, "doGoogleAuth: ")

        return repository.doGoogleAuth(authCredential)

    }

    private var firstPage = 1
     var firstPage2 = 1

    val tvMovieList = mutableStateListOf<Results>()

    val tvMovieWithGenreList = mutableStateListOf<Results>()
    val favouriteMovies = mutableStateListOf<FavouriteMovie>()
    val searchResults = mutableStateListOf<SearchResult>()

    fun getPageWithGenre(newPage: String, id: String, tv: Boolean) {

        Log.d(TAG, "getPageWithGenre: $newPage")

        if (firstPage == newPage.toInt()) {

            firstPage++

            CoroutineScope(Dispatchers.IO).launch {

                if (tv) {
                    repository.getTvWithGenres(id, newPage).collectLatest {

                        it.result?.let { it1 -> tvMovieWithGenreList.addAll(it1.results) }
                    }

                } else {
                    repository.getMoviesWithGenres(id, newPage).collectLatest {

                        it.result?.let { it1 -> tvMovieWithGenreList.addAll(it1.results) }
                    }
                }

            }
        }

    }

    fun authState(): Flow<Boolean> {

        return repository.isAuthenticated()
    }

    fun getUserDetails(): Flow<User?> {

        return repository.getUserDetails()

    }

    fun getTrending(): Flow<NetworkResult<Movies?>> {

        return repository.getTrending()

    }

    fun getCategory(): Flow<NetworkResult<Category?>> {

        return repository.getCategory()

    }

    fun getCategoryTv(): Flow<NetworkResult<Category?>> {

        return repository.getCategoryTv()

    }

    fun getMoviesWithGenres(id: String, page: String): Flow<NetworkResult<Movies?>> {

        return repository.getMoviesWithGenres(id, page)

    }

    fun getTvWithGenres(id: String, page: String): Flow<NetworkResult<Movies?>> {

        return repository.getTvWithGenres(id, page)

    }

    fun getPage(newPage: String, id: String) {

        Log.d(TAG, "getPage: $newPage")
        Log.d(TAG, "getPage id: $id")

        if (firstPage2 == newPage.toInt()) {

            firstPage2++

            CoroutineScope(Dispatchers.IO).launch {

                repository.getTvWithNetwork(id, newPage).collectLatest {

                    if (it.result != null) {
                        tvMovieList.addAll(it.result.results)
                        Log.d(TAG, "getPage size: " + tvMovieList.size)
                    }
                }
            }
        }
    }

    fun getMovieDetails(id: String): Flow<NetworkResult<MovieDetails?>> {

        return repository.getMovieDetails(id)

    }

    fun getMovieCredits(id: String): Flow<NetworkResult<Credits?>> {

        return repository.getMovieCredits(id)

    }

    fun getTvDetails(id: String): Flow<NetworkResult<TvDetails?>> {

        return repository.getTvDetails(id)

    }

    fun getTvCredits(id: String): Flow<NetworkResult<CreditsTv?>> {

        return repository.getTvCredits(id)

    }

    fun addToFavouriteMovies(favouriteMovie: FavouriteMovie) {

        repository.addToFavouriteMovies(favouriteMovie)
    }

    fun checkIfIsAlreadyLiked(id: String?): Flow<Boolean> {

        return callbackFlow {

            val documentReference = id?.let {
                firestore.collection("Favourites")
                    .document(auth.currentUser?.uid.toString())
                    .collection("MyFavourites")
                    .document(it)
            }

            documentReference?.addSnapshotListener { value, e ->

                if (value != null) {
                    trySend(value.exists())
                }

            }

            awaitClose { }

        }

    }

    fun getLikedMovies() {

        favouriteMovies.clear()

        val collectionReference = firestore.collection("Favourites")
            .document(auth.currentUser?.uid.toString())
            .collection("MyFavourites").orderBy("firebaseId", Query.Direction.DESCENDING)

        collectionReference.addSnapshotListener { value, e ->

            if (value != null) {

                for (document in value.documents) {

                    val favouriteMovie = document.toObject(FavouriteMovie::class.java)

                    if (favouriteMovie != null) {
                        favouriteMovies.add(favouriteMovie)
                    }

                }
            }

        }

    }

    fun updateProfile(name: String?, uri: Uri?) {
        repository.updateProfile(name, uri)
    }

    fun search(keyword: String, page: String) {

        CoroutineScope(Dispatchers.IO).launch {

            repository.search(keyword, page).collectLatest {

                if (it != null) {
                    it.result?.let { it1 -> searchResults.addAll(it1.results) }
                }

            }
        }
    }


}