package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.Results
import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.MovieItem
import com.larrex.panorama.ui.screens.component.ProviderChip
import com.larrex.panorama.ui.viewmodel.MainViewModel

private const val TAG = "TvShow"

@Composable
fun TvShows(navHostController: NavHostController) {

    val listOfChips = listOf(
        "Any",
        "Prime Video",
        "Apple Tv+",
        "Disney+",
        "Discovery+",
        "Hulu",
        "HBO Max",
        "Netflix",
        "Google Play",
        "Show Max"
    )

    val listOfChipsIds = listOf(
        0,
        1024,
        2552,
        2739,
        4353,
        453,
        3186,
        213,
        1102,
        2265
    )

    var selected by remember() {
        mutableStateOf("Any")
    }
    var loadMore by remember() {
        mutableStateOf(false)
    }
    var selectedID by remember() {
        mutableStateOf(0)
    }
    var page by remember() {
        mutableStateOf(1)
    }

    val viewModel = hiltViewModel<MainViewModel>()

    val categoryTv by viewModel.getCategoryTv().collectAsState(initial = null)

    val movies: MutableList<Movies?> = ArrayList<Movies?>()

    categoryTv?.genres?.forEach {

        val movie by viewModel.getTvWithGenres(it.id.toString()).collectAsState(initial = null)

        movies.add(movie)
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        if (selected == "Any") {

            LazyColumn(contentPadding = PaddingValues(bottom = 70.dp, top = 150.dp)) {

                categoryTv?.let {
                    itemsIndexed(it.genres) { index, it ->

                        it.name?.let { it1 ->
                            CategoryItem(
                                it1, false, navHostController,
                                movies[index]
                            )
                        }

                    }
                }

            }
        } else {

            val tv: MutableList<Results?> = ArrayList<Results?>()

            val networkShows by viewModel.getTvWithNetwork(
                id = selectedID.toString(),
                page = page.toString()
            ).collectAsState(initial = null)

            if (networkShows != null) {

                for (tvShow in networkShows!!.results) {

                    tv.add(tvShow)
                }

            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 70.dp, top = 150.dp)
            ) {

                itemsIndexed(tv) { index, item ->

                    loadMore = tv.size - 1 == index

                    if (tv.size - 1 == index) {

                        Log.d(TAG, "TvShows: $page")

                        loadMore = false

                    }
                    if (item != null) {
                        MovieItem(
                            tv = false,
                            imageUrl = "https://image.tmdb.org/t/p/w780" + item.posterPath
                        ) {
                            page += 1
                        }
                    }

                }

//                item {
//
//                    Box(modifier = Modifier){
//
//                        Button(onClick = { /*TODO*/ }) {
//
//                            Text(text = )
//
//                        }
//
//                    }
//
//                }

            }

        }

        Surface(color = Color.Black.copy(alpha = 0.6f)) {

            Column() {

                Text(
                    text = "Tv Shows.", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 35.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp),
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp)

                ) {
                    itemsIndexed(listOfChips) { index, it ->

                        ProviderChip(
                            it,
                            chipSelected = it == selected,
                            onChipSelected = {

                                selected = it
                                selectedID = listOfChipsIds[index]
                                page = 1

                            }, modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(30.dp))
                        )
                    }
                }
            }
        }

        LaunchedEffect(loadMore) {

            Log.d(TAG, "TvShows: $loadMore")

            if (loadMore) {
            }

            loadMore = false

        }
    }
}

