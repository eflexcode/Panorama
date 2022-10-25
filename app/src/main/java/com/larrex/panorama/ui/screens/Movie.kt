package com.larrex.panorama.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.larrex.panorama.domain.retrofit.model.Movies

import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.TrendingItem
import com.larrex.panorama.ui.viewmodel.MainViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalSnapperApi::class, ExperimentalPagerApi::class)
@Composable
fun Movies() {
    val viewModel = hiltViewModel<MainViewModel>()

    val movies: MutableList<Movies?> = ArrayList<Movies?>()

//    val user by viewModel.getUserDetails().collectAsState(initial = null)
    val trending by viewModel.getTrending().collectAsState(initial = null)
    val category by viewModel.getCategory().collectAsState(initial = null)


//    val painter = rememberAsyncImagePainter(
//        model = user?.imageUrl,
//        placeholder = painterResource(id = R.drawable.gray),
//        error = painterResource(id = R.drawable.gray)
//    )

    category?.genres?.forEach {

        val movie by viewModel.getMoviesWithGenres(it.id.toString()).collectAsState(initial = null)

        movies.add(movie)
    }

    val state = rememberLazyListState()
    val behavior = rememberSnapperFlingBehavior(state)

    val uiControl = rememberSystemUiController()

    uiControl.setSystemBarsColor(Color.Black)


    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        LazyColumn(contentPadding = PaddingValues(bottom = 70.dp)) {

            item {

                LazyRow(modifier = Modifier, state, flingBehavior = behavior) {

                    if (trending != null) {

                        items(trending!!.results) {

                            it.title?.let { it1 ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp)
                                        .background(
                                            Color.Yellow,
                                            RoundedCornerShape(
                                                bottomStart = 20.dp,
                                                bottomEnd = 20.dp
                                            )
                                        )
                                ) {
                                    TrendingItem(
                                        imageUrl = "https://image.tmdb.org/t/p/w780" + it.posterPath,
                                        it1
                                    ) {


                                    }
                                }
                            }
                        }
                    }
                }
            }

            category?.let {
                itemsIndexed(it.genres) { index,item ->

                    item.name?.let { it1 -> item.id?.let { it2 -> CategoryItem(it1, movies[index]) } }

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