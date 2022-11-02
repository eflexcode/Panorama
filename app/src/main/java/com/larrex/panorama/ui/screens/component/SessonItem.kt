package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.domain.retrofit.model.moviedetails.Seasons
import com.larrex.panorama.domain.retrofit.model.moviedetails.TvDetails
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.Green
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@Composable
fun SessionItem(seasons: Seasons) {

    Box(
        modifier = Modifier
            .width(400.dp)
            .height(200.dp)
            .padding(3.dp)
            .background(ChipBackground, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.TopEnd
    ) {

        Row() {

            GlideImage(
                imageModel = { "https://image.tmdb.org/t/p/w780" + seasons.posterPath },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .toggleable(
                        value = true,
                        enabled = true,
                        role = null,
                        onValueChange = {

                        }), component = imageComponent {

                    +PlaceholderPlugin.Loading(painterResource(id = R.drawable.gray))
                    +PlaceholderPlugin.Failure(painterResource(id = R.drawable.gray))
                    +CrossfadePlugin(duration = 350)

                }

            )


            Column {

                Text(
                    text = seasons.name + "",
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)

                )

                Text(
                    text = seasons.airDate + "",
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Util.quicksand,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 10.dp)

                )

                Column(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxHeight()) {
                    Text(
                        text = seasons.overview + "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 20.dp, start = 20.dp, top = 0.dp, bottom = 10.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Util.quicksand

                    )
                }


            }

        }
//        if (tv) {
//
//            Box(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .size(35.dp)
//                    .padding(7.dp)
//                    .background(Green, CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//
//                Text(
//                    text = "Tv", color = Color.White,
//                    fontFamily = Util.quicksand,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 10.sp,
//                    modifier = Modifier.padding(0.dp)
//                )
//
//            }
//        }

    }

}

@Preview(showBackground = true)
@Composable()
fun TvDetailsPr() {
//    SessionItem("")
}