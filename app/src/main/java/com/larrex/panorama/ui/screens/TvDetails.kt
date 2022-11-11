package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.domain.model.FavouriteMovie
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.moviedetails.MovieDetails
import com.larrex.panorama.ui.screens.component.CastItem
import com.larrex.panorama.ui.screens.component.SessionItem
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.Green
import com.larrex.panorama.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

private const val TAG = "TvDetails"

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun TvDetails(id: String) {

    val colors = listOf<Color>(Color.White, Color.Black)
    val list = listOf<String>("Action", "Adventure", "Fantasy")

    val viewModel = hiltViewModel<MainViewModel>()

    val state = rememberLazyListState()
    val behavior = rememberSnapperFlingBehavior(state)

    Log.d(TAG, "TvDetails: " + id)

    val tvDetails by viewModel.getTvDetails(id).collectAsState(initial = null)
    val credits by viewModel.getTvCredits(id).collectAsState(initial = null)

    var isFavourite by remember { mutableStateOf(false) }

    val animatedColor = animateColorAsState(
        targetValue = if (isFavourite) Green else ChipBackground,
        animationSpec = tween(1000, 0, LinearEasing)
    )
    if (tvDetails != null) {

        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .height(450.dp), contentAlignment = Alignment.Center
                ) {

                    GlideImage(
                        imageModel = { "https://image.tmdb.org/t/p/w780" + tvDetails?.posterPath },
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {

                                val gradient = Brush.verticalGradient(colors)

                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient, blendMode = BlendMode.Multiply)
                                }

                            },
                    )

                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .background(
                                Color.Black.copy(0.6f),
                                CircleShape
                            )
                            .size(70.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )

                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Surface(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        color = Green

                    ) {

                        Text(
                            text = "TMDB",
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = Util.quicksand,
                            modifier = Modifier.padding(
                                top = 10.dp,
                                end = 30.dp,
                                start = 30.dp,
                                bottom = 10.dp
                            )
                        )

                    }

                    Text(
                        text = "Rating " + tvDetails?.voteAverage,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        fontFamily = Util.quicksand,
                        modifier = Modifier.padding(start = 15.dp)
                    )

                    IconButton(
                        onClick = {


                            val firebaseId = System.currentTimeMillis().toString()

                            val favouriteMovie = FavouriteMovie(
                                firebaseId = firebaseId,
                                tvDetails!!.adult,
                                tvDetails!!.backdropPath,
                                tvDetails!!.id,
                                tvDetails!!.name,
                                tvDetails!!.originalLanguage,
                                tvDetails!!.originalName,
                                tvDetails!!.overview,
                                tvDetails!!.posterPath,
                                tvDetails!!.type,
                                tvDetails!!.popularity
                            )

                            viewModel.addToFavouriteMovies(favouriteMovie)
                            isFavourite = true
                        },
                    ) {

                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_heart_selceted
                            ),
                            contentDescription = null,
                            tint = animatedColor.value,
                            modifier = Modifier.size(20.dp)
                        )

                    }

                }

                Text(
                    text = tvDetails?.name + "",
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)

                )

                FlowRow(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 10.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                ) {

                    tvDetails?.genres?.forEach {

                        Surface(
                            modifier = Modifier
                                .padding(end = 3.dp, start = 3.dp, top = 3.dp, bottom = 3.dp)
                                .clip(RoundedCornerShape(30.dp)),
                            color = ChipBackground

                        ) {

                            Text(
                                text = it.name + "",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                fontFamily = Util.quicksand,
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    end = 20.dp,
                                    start = 20.dp,
                                    bottom = 10.dp
                                )
                            )

                        }
                    }

                }

                Text(
                    text = tvDetails?.overview + "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, start = 20.dp, top = 0.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Util.quicksand

                )

                Text(
                    text = "Seasons",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)

                )

                LazyRow(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 10.dp,
                        bottom = 0.dp
                    ), state = state, flingBehavior = behavior
                ) {

                    tvDetails?.let {
                        items(it.seasons) {
                            SessionItem(seasons = it)

                        }
                    }


                }

                Text(
                    text = "Cast",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)

                )

                LazyRow(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 10.dp,
                        bottom = 100.dp
                    )
                ) {

                    credits?.let {
                        items(it.cast) {

                            CastItem(
                                imageUrl = "https://image.tmdb.org/t/p/w780" + it.profilePath,
                                name = it.name + ""
                            ) {

                            }

                        }
                    }


                }

            }

        }
    } else {

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {

            CircularProgressIndicator(color = Color.White)

        }
    }

}