package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryItem(categoryName: String,tv : Boolean,navController: NavController, movies: Movies?) {

    val viewModel = hiltViewModel<MainViewModel>()


//    CoroutineScope(Dispatchers.IO).launch {
//
//    }

//    val movies by viewModel.getMoviesWithGenres(id).collectAsState(initial = null)
//    var movies : Movies? = null

    LaunchedEffect(Unit) {

//        val movies2 = viewModel.getMoviesWithGenres(id).collectLatest { item ->
////            movies = item
//        }

    }


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

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.3f)) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = null,
                        tint = Color.White
                    )

                }

            }

            LazyRow(contentPadding = PaddingValues(start = 15.dp, end = 30.dp)) {

                movies?.let {
                    items(it.results, contentType = { Results() }) {

                        MovieItem(
                            tv = tv,
                            imageUrl = "https://image.tmdb.org/t/p/w780" + it.posterPath,
                        ) {

                            navController.navigate(NavScreens.MovieDetails.route)

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