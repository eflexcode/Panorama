package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.screens.component.MovieItem
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.viewmodel.MainViewModel

@Composable
fun Favorite(navController: NavController) {
    val viewModel = hiltViewModel<MainViewModel>()
    viewModel.getLikedMovies()

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(bottom = 60.dp, top = 80.dp)
        ) {

            itemsIndexed(viewModel.favouriteMovies) { index, item ->

                item.tv?.let {
                    MovieItem(
                        tv = it,
                        imageUrl = "https://image.tmdb.org/t/p/w342" + item.posterPath
                    ) {

                        if (item.tv!!) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "tvId",
                                item.id.toString()
                            )

                            navController.navigate(NavScreens.TvDetails.route)

                        } else {

                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "movieId",
                                item.id.toString()
                            )

                            navController.navigate(NavScreens.MovieDetails.route)

                        }

                    }
                }

            }
        }


        Surface(color = Color.Black.copy(alpha = 0.6f)) {

            Column() {

                Text(
                    text = "My Likes.", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 35.dp, bottom = 10.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand
                )


            }
        }

    }

}