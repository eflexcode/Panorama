package com.larrex.panorama.ui.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.core.NetworkResult
import com.larrex.panorama.core.Status
import com.larrex.panorama.domain.retrofit.model.Movies

import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.TrendingIndicator
import com.larrex.panorama.ui.screens.component.TrendingItem
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.viewmodel.MainViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

private const val TAG = "Movie"

@OptIn(ExperimentalSnapperApi::class, ExperimentalPagerApi::class)
@Composable
fun Movies(navController: NavController, application: Application) {

    val viewModel = hiltViewModel<MainViewModel>()

    val trending by viewModel.getTrending()
        .collectAsState(initial = NetworkResult(Status.LOADING, null))
    val category by viewModel.getCategory().collectAsState(initial = null)

    var currentIndicator by remember {
        mutableStateOf(0)
    }

    val list: MutableList<Int> = ArrayList<Int>()

    val state = rememberLazyListState()
    val behavior = rememberSnapperFlingBehavior(state)

    val uiControl = rememberSystemUiController()

    uiControl.setSystemBarsColor(Color.Black)

    val selected by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo.lastIndex + state.firstVisibleItemIndex } }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        if (trending?.status == Status.LOADING) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {

                CircularProgressIndicator(color = Color.White)

            }
        } else if (trending.status == Status.FAILURE) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()

            ) {

                Text(
                    text = "Your offline.", modifier = Modifier
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

        } else if (trending.status == Status.SUCCESS){

            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {

                Column(Modifier.padding(bottom = 70.dp)) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LazyRow(
                            modifier = Modifier, state, flingBehavior = behavior
                        ) {

                            trending.result?.let {
                                itemsIndexed(it.results) { index, it ->

                                    currentIndicator += 1

                                    it.title?.let { it1 ->

                                        it.overview?.let { it2 ->
                                            TrendingItem(
                                                imageUrl = "https://image.tmdb.org/t/p/w780" + it.posterPath,
                                                it1,
                                                it2,
                                                application
                                            ) {

                                                Log.d(TAG, "Movies: " + list)
                                                //                                                Log.d(TAG, "Movies: " + trending!!.results.size)

                                            }
                                        }
                                    }
                                }
                            }
                        }

                        LazyRow() {
                            trending.result?.let {
                                itemsIndexed(it.results) { index, it ->


                                    if (state.layoutInfo.visibleItemsInfo.size > 0) {

                                        val selected by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo[0].index == index } }
                                        TrendingIndicator(selected)
                                    }
                                }
                            }

                        }

                    }

                    category?.result?.genres?.forEach { item ->
                        val movies by viewModel.getMoviesWithGenres(item.id.toString(), "1")
                            .collectAsState(initial = null)

                        item.id?.let {
                            CategoryItem(
                                item.name.toString(), false, navController, movies?.result, it
                            )
                        }
                    }


                }

            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun A() {
    Movies()
}