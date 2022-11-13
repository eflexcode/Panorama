package com.larrex.panorama.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

                        if (it != null) {
                            tvMovieWithGenreList.addAll(it.results)
                        }
                    }

                } else {
                    repository.getMoviesWithGenres(id, newPage).collectLatest {

                        if (it != null) {
                            tvMovieWithGenreList.addAll(it.results)
                        }
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

    fun getTrending(): Flow<Movies?> {

        return repository.getTrending()

    }

    fun getCategory(): Flow<Category?> {

        return repository.getCategory()

    }

    fun getCategoryTv(): Flow<Category?> {

        return repository.getCategoryTv()

    }

    fun getMoviesWithGenres(id: String, page: String): Flow<Movies?> {

        return repository.getMoviesWithGenres(id, page)

    }

    fun getTvWithGenres(id: String, page: String): Flow<Movies?> {

        return repository.getTvWithGenres(id, page)

    }

    fun getPage(newPage: String, id: String) {

        Log.d(TAG, "getPage: $newPage")
        Log.d(TAG, "getPage: $id")

        CoroutineScope(Dispatchers.IO).launch {

            repository.getTvWithNetwork(id, newPage).collectLatest {

                if (it != null) {
                    tvMovieList.addAll(it.results)
                    Log.d(TAG, "getPage: " + tvMovieList.size)
                }
            }

        }

    }

    fun getMovieDetails(id: String): Flow<MovieDetails?> {

        return repository.getMovieDetails(id)

    }

    fun getMovieCredits(id: String): Flow<Credits?> {

        return repository.getMovieCredits(id)

    }

    fun getTvDetails(id: String): Flow<TvDetails?> {

        return repository.getTvDetails(id)

    }

    fun getTvCredits(id: String): Flow<CreditsTv?> {

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
                    searchResults.addAll(it.results)
                }

            }
        }
    }


}