package com.larrex.panorama.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.Green
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@Composable
fun MovieDetails() {

    val colors = listOf<Color>(Color.White, Color.Black)

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(450.dp), contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithCache {

                            val gradient = Brush.verticalGradient(colors)

                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }

                        }, contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
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
                    text = "Rating 6.3",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontFamily = Util.quicksand,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }

            Text(
                text = "All quiet on the western front",
                textAlign = TextAlign.Start,
                fontSize = 35.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = Util.quicksand,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 0.dp)

            )

            Row(modifier = Modifier.padding(top = 10.dp)) {
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

            }

            Text(
                text = "Watch your favorite movies or series on only one platformfavorite movies or series on only one platformfavorite movies or series on only one platformfavorite movies or series on only one platform. You can watch it anytime and anywhere.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp, start = 20.dp, top = 0.dp),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontFamily = Util.quicksand

            )

        }

    }

}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun MovieDetailsPre() {
    MovieDetails()
}