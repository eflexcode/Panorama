package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
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

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        LazyColumn() {

            item {

//                Row(
//                    modifier = Modifier
//                        .padding(start = 15.dp, top = 10.dp)
//                        .fillMaxWidth(), horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Image(
//                        painter = painter,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(50.dp)
//                            .clip(CircleShape)
//                            .toggleable(
//                                value = true,
//                                enabled = true,
//                                role = null,
//                                onValueChange = {
//
//                                }), contentScale = ContentScale.Crop,
//                        alignment = Alignment.Center
//                    )
//
//
//                Column(modifier = Modifier.padding(start = 10.dp)) {
//
//                    Text(
//                        text = Util.getGreeting(),
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        textAlign = TextAlign.Start,
//                        fontSize = 15.sp,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Medium,
//                        fontFamily = Util.quicksand
//                    )
//
//                    Text(
//                        text = user?.name.toString(), modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Start,
//                        fontSize = 18.sp,
//                        color = Color.Black,
//
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = Util.quicksand
//
//                    )
//                }
//
//
//
//            }

                Column() {

                    Text(
                        text = "Panorama.", modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Util.quicksand

                    )

                    LazyRow(modifier = Modifier.padding(top = 20.dp),state, flingBehavior = behavior) {

                        if (trending != null) {

                            items(trending!!.results) {

                                it.title?.let { it1 ->
                                    TrendingItem(
                                        imageUrl = "https://image.tmdb.org/t/p/w780" + it.backdropPath,
                                        it1
                                    ) {


                                    }
                                }
                            }
                        }

                    }

//                    trending?.results?.let {
//                        HorizontalPager(5) { page ->
//
//                            trending!!.results[page].title?.let { it1 ->
//                                TrendingItem(
//                                    Modifier
//                                        .graphicsLayer {
//
//                                            val pageOffset =
//                                                calculateCurrentOffsetForPage(page).absoluteValue
//
//                                            // We animate the scaleX + scaleY, between 85% and 100%
//                                            lerp(
//                                                start = 0.90f,
//                                                stop = 1f,
//                                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                            ).also { scale ->
//                                                scaleX = scale
//                                                scaleY = scale
//                                            }
//
//                                            // We animate the alpha, between 50% and 100%
//                                            alpha = lerp(
//                                                start = 0.5f,
//                                                stop = 1f,
//                                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                            )
//                                        },
//                                    imageUrl = "https://image.tmdb.org/t/p/w780" + trending!!.results[page].backdropPath,
//                                    it1
//                                ) {
//
//                                }
//                            }
//
//
//                        }
//                    }


                }

            }

            category?.let {
                items(it.genres){

                    it.name?.let { it1 -> it.id?.let { it2 -> CategoryItem(it1, it2) } }

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