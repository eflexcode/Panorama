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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.Genres
import com.larrex.panorama.ui.screens.component.MovieItem
import com.larrex.panorama.ui.screens.navigation.CategoryType
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.viewmodel.MainViewModel

private const val TAG = "MovieGenre"

@Composable
fun MovieGenre(navController: NavController, type: CategoryType) {

    val viewModel = hiltViewModel<MainViewModel>()
    var page = 1
    viewModel.getPageWithGenre(page.toString(), type.id.toString(), type.tv)

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(bottom = 70.dp, top = 70.dp)
        ) {


            itemsIndexed(viewModel.tvMovieWithGenreList) { index, item ->

                if (viewModel.tvMovieWithGenreList.size - 1 == index) {

                    page += 1

                    viewModel.getPageWithGenre(page.toString(), type.id.toString(), type.tv)

                }

                MovieItem(
                    tv = type.tv,
                    imageUrl = "https://image.tmdb.org/t/p/w342" + item.posterPath
                ) {

                    if (type.tv) {
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

        Surface(color = Color.Black.copy(alpha = 0.6f)) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 20.dp)
            ) {

                IconButton(
                    onClick = { navController.popBackStack() }, modifier = Modifier
                        .padding(top = 0.dp, end = 5.dp, start = 5.dp, bottom = 0.dp)
                        .weight(0.3f)
                        .size(50.dp)
                ) {

                    Icon(
                        painter = painterResource(id = com.larrex.panorama.R.drawable.ic_back),
                        contentDescription = null
                    )

                }

                Text(
                    text = type.name + "",
                    fontSize = 18.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 5.dp, start = 5.dp, top = 0.dp)
                        .weight(2f)
                )
            }
        }

    }
}