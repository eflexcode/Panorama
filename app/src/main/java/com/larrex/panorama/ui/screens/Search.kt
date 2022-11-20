package com.larrex.panorama.ui.screens

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.screens.component.MovieItem
import com.larrex.panorama.ui.screens.component.ProviderChip
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController) {

    var newText by remember { mutableStateOf(TextFieldValue("")) }
    var page = 1
    val viewModel = hiltViewModel<MainViewModel>()

    val handler = Handler()

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        if (viewModel.searchResults.isNotEmpty()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 60.dp, top = 150.dp)
            ) {

                itemsIndexed(viewModel.searchResults) { index, item ->

                    if (viewModel.searchResults.size - 1 == index) {

                        page += 1

                        viewModel.search(newText.text, page.toString())

                    }

                    MovieItem(
                        tv = item.mediaType == "tv",
                        imageUrl = "https://image.tmdb.org/t/p/w342" + item.posterPath
                    ) {

                        if (item.mediaType == "tv") {
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
        } else {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()

            ) {

                Text(
                    text = "Start typing.", modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = ChipBackground,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,

                )

                Image(
                    painter = painterResource(id = R.drawable.typing),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                )

            }

        }

        Surface(color = Color.Black.copy(alpha = 0.6f)) {

            Column() {

                Text(
                    text = "Search.", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 35.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand
                )

                TextField(
                    value = newText,
                    onValueChange = { text ->

                        newText = text
                        viewModel.searchResults.clear()
                        viewModel.search(newText.text, "1")


                    },
                    modifier = Modifier
                        .padding(top = 10.dp, end = 20.dp, start = 20.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        contentColorFor(backgroundColor = Color.Transparent),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = ChipBackground,
                        placeholderColor = Color.Gray,
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            text = "Start typing",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    },
                    textStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = Util.quicksand,
                        fontSize = 12.sp,
                        color = Color.White
                    ),

                    )

            }
        }

    }

}