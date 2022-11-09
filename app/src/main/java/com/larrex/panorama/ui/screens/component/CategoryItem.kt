package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.larrex.panorama.Util
import com.larrex.panorama.R
import com.larrex.panorama.domain.retrofit.model.Movies
import com.larrex.panorama.domain.retrofit.model.Results
import com.larrex.panorama.ui.screens.navigation.CategoryType
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CategoryItem(categoryName: String, tv: Boolean, navController: NavController, movies: Movies?,id:Int) {

    val type = CategoryType(id, categoryName,tv)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(bottom = 0.dp, top = 0.dp)
    ) {

        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = categoryName,
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 15.dp, end = 10.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    maxLines = 2,

                    )

                IconButton(onClick = {

                        navController.currentBackStackEntry?.savedStateHandle?.set("type" , type)
                        navController.navigate(NavScreens.MovieDetails.route)

                }, modifier = Modifier.weight(0.3f)) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = null,
                        tint = Color.White
                    )

                }

            }

            if (movies == null) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .height(203.dp)
                ) {

                    CircularProgressIndicator(color = Color.White)

                }

            } else {

                LazyRow(contentPadding = PaddingValues(start = 15.dp, end = 30.dp)) {

                    items(movies.results, contentType = { Results() }) {

                        MovieItem(
                            tv = tv,
                            imageUrl = "https://image.tmdb.org/t/p/w780" + it.posterPath,
                        ) {
                            if (tv) {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "tvId",
                                    it.id.toString()
                                )

                                navController.navigate(NavScreens.TvDetails.route)
                            } else {

                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "id",
                                    it.id.toString()
                                )
                                navController.navigate(NavScreens.MovieDetails.route)
                            }
                        }

                    }

                }

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
//    CategoryItem("", 0)
}