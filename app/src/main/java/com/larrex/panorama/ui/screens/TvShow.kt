package com.larrex.panorama.ui.screens

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.core.NetworkResult
import com.larrex.panorama.core.Status
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.Results
import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.MovieItem
import com.larrex.panorama.ui.screens.component.ProviderChip
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.Green
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
        "Paramount+",
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
        4330,
        2265
    )

    var page = 1

    var selected by remember() {
        mutableStateOf("Any")
    }

    var selectedID by remember() {
        mutableStateOf(0)
    }

    val viewModel = hiltViewModel<MainViewModel>()

    val categoryTv by viewModel.getCategoryTv().collectAsState(NetworkResult(Status.LOADING, null))
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        if (categoryTv.status == Status.LOADING) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {

                CircularProgressIndicator(color = Color.White)

            }
        } else if (categoryTv.status == Status.FAILURE) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()

            ) {

                Text(
                    text = "Your offline.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = ChipBackground,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,

                    )

                Image(
                    painter = painterResource(id = R.drawable.no_wifi),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                )

            }

        } else {

            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {

                if (selected == "Any") {

                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 70.dp, top = 150.dp)
                    ) {

                        categoryTv.result?.genres?.forEach {
                            val movie by viewModel.getTvWithGenres(it.id.toString(), "1")
                                .collectAsState(initial = null)
                            it.name?.let { it1 ->
                                it.id?.let { it2 ->
                                    CategoryItem(
                                        it1, true, navHostController,
                                        movie?.result, it2
                                    )
                                }
                            }
                        }
                    }

                } else {

//                viewModel.tvMovieList.clear()

                    Column {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(bottom = 70.dp, top = 150.dp)
                        ) {

                            itemsIndexed(viewModel.tvMovieList) { index, item ->

                                if (viewModel.tvMovieList.size - 1 == index) {

                                    page ++

                                    Log.d(
                                        TAG,
                                        "TvShows: " + index + " list size " + viewModel.tvMovieList.size + " page " + page

                                    )

                                    viewModel.getPage(viewModel.firstPage2.toString(), selectedID.toString())

                                }

                                MovieItem(
                                    tv = true,
                                    imageUrl = "https://image.tmdb.org/t/p/w342" + item.posterPath
                                ) {

                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "tvId",
                                        item.id.toString()
                                    )

                                    navHostController.navigate(NavScreens.TvDetails.route)
                                }

                            }
                        }

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
                                        viewModel.tvMovieList.clear()
                                        viewModel.firstPage2 = 1
                                        selected = it
                                        selectedID = listOfChipsIds[index]

                                        viewModel.getPage("1", selectedID.toString())
                                        page = 1

                                    }, modifier = Modifier
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

