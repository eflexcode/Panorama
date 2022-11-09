package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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

        Column(
            modifier = Modifier.padding(top = 25.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 70.dp, top = 0.dp)
            ) {


                itemsIndexed(viewModel.tvMovieWithGenreList) { index, item ->

                    if (viewModel.tvMovieWithGenreList.size - 1 == index) {

                        page+=1

                        Log.d(TAG, "MovieGenre: $page")

                        viewModel.getPageWithGenre(page.toString(), type.id.toString(), type.tv)

                    }

                    MovieItem(
                        tv = false,
                        imageUrl = "https://image.tmdb.org/t/p/w780" + item.posterPath
                    ) {

                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "tvId",
                            item.id.toString()
                        )

                        navController.navigate(NavScreens.TvDetails.route)
                    }

                }
            }

        }
    }
}