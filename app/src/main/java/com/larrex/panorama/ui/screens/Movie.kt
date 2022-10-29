package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.larrex.panorama.R
import com.larrex.panorama.domain.retrofit.model.Movies

import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.TrendingIndicator
import com.larrex.panorama.ui.screens.component.TrendingItem
import com.larrex.panorama.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "Movie"

@OptIn(ExperimentalSnapperApi::class, ExperimentalPagerApi::class)
@Composable
fun Movies(navController: NavController) {
    val viewModel = hiltViewModel<MainViewModel>()

    val movies: MutableList<Movies?> = ArrayList<Movies?>()

    val trending by viewModel.getTrending().collectAsState(initial = null)
    val category by viewModel.getCategory().collectAsState(initial = null)

    var currentIndicator by remember {
        mutableStateOf(0)
    }

    val list: MutableList<Int> = ArrayList<Int>()

    category?.genres?.forEach {

        val movie by viewModel.getMoviesWithGenres(it.id.toString()).collectAsState(initial = null)

        movies.add(movie)
    }

    val state = rememberLazyListState()
    val behavior = rememberSnapperFlingBehavior(state)

//    state.layoutInfo.visibleItemsInfo.last().index

    val uiControl = rememberSystemUiController()

    uiControl.setSystemBarsColor(Color.Black)

    val selected by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo.lastIndex + state.firstVisibleItemIndex } }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        LazyColumn(contentPadding = PaddingValues(bottom = 70.dp)) {

            item {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyRow(
                        modifier = Modifier, state, flingBehavior = behavior
                    ) {

                        if (trending != null) {

                            itemsIndexed(trending!!.results) { index, it ->

                                currentIndicator += 1

                                it.title?.let { it1 ->

                                    it.overview?.let { it2 ->
                                        TrendingItem(
                                            imageUrl = "https://image.tmdb.org/t/p/w780" + it.posterPath,
                                            it1, it2
                                        ) {

                                            Log.d(TAG, "Movies: " + list)
                                            Log.d(TAG, "Movies: " + trending!!.results.size)

                                        }
                                    }
                                }

                            }
                        }
                    }

                    LazyRow() {

                        items(13) {

                            TrendingIndicator(state.firstVisibleItemIndex == it)

                        }

                    }

                }

            }

            category?.let {
                itemsIndexed(it.genres) { index, item ->

                    item.name?.let { it1 ->
                        item.id?.let { it2 ->
                            CategoryItem(
                                it1, false,navController,
                                movies[index]
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