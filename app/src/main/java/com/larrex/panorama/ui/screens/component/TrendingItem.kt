package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.absoluteValue

@Composable
fun TrendingItem(imageUrl: String, name: String, onClick: () -> Unit) {

    val colors = listOf<Color>(Color.White, Color.Black)

    Box(
        modifier = Modifier
            .fillMaxWidth().background(Color.Black)
            .height(450.dp), contentAlignment = Alignment.BottomCenter
    ) {

        GlideImage(
            imageModel = {imageUrl},
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {

                    val gradient = Brush.verticalGradient(colors)

                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }

                }
                .toggleable(
                    value = true,
                    enabled = true,
                    role = null,
                    onValueChange = {

                        onClick()

                    }),
        )

        Text(
            text = name, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, end = 0.dp, bottom = 50.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = Util.quicksand,
            maxLines = 2,


        )

    }
}

@Preview(showBackground = true)
@Composable
fun TrendingPreview() {

//    TrendingItem("https://image.tmdb.org/t/p/w780/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg", "") {

//    }

}