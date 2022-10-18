package com.larrex.panorama.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.screens.component.TrendingItem
import com.larrex.panorama.ui.viewmodel.MainViewModel

@Composable
fun Movies() {
    val viewModel = hiltViewModel<MainViewModel>()

    val user by viewModel.getUserDetails().collectAsState(initial = null)
    val trending by viewModel.getTrending().collectAsState(initial = null)

    val painter = rememberAsyncImagePainter(
        model = user?.imageUrl,
        placeholder = painterResource(id = R.drawable.gray),
        error = painterResource(id = R.drawable.gray)
    )

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

                    LazyRow(modifier = Modifier.padding(top = 20.dp)) {

                        if (trending != null) {

                            items(trending!!.results) {

                                it.title?.let { it1 ->
                                    TrendingItem(imageUrl = "https://image.tmdb.org/t/p/w780" + it.backdropPath,
                                        it1
                                    ) {



                                    }
                                }
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
fun A() {
    Movies()
}