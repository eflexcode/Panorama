package com.larrex.panorama.ui.screens

import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Category
import com.larrex.panorama.ui.screens.component.CategoryItem
import com.larrex.panorama.ui.screens.component.TrendingItem
import com.larrex.panorama.ui.viewmodel.MainViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.absoluteValue


@OptIn(ExperimentalSnapperApi::class, ExperimentalPagerApi::class)
@Composable
fun Movies() {
    val viewModel = hiltViewModel<MainViewModel>()

    val user by viewModel.getUserDetails().collectAsState(initial = null)
    val trending by viewModel.getTrending().collectAsState(initial = null)
    val category by viewModel.getCategory().collectAsState(initial = null)

    val painter = rememberAsyncImagePainter(
        model = user?.imageUrl,
        placeholder = painterResource(id = R.drawable.gray),
        error = painterResource(id = R.drawable.gray)
    )

    val state = rememberLazyListState()
    val behavior = rememberSnapperFlingBehavior(state)


    val uiControl = rememberSystemUiController()

    uiControl.setSystemBarsColor(Color.Black)

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        LazyColumn() {

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
                items(it.genres) {
                    val movies by viewModel.getMoviesWithGenres(it.id.toString())
                        .collectAsState(initial = null)
                    it.name?.let { it1 -> it.id?.let { it2 -> CategoryItem(it1, movies) } }

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