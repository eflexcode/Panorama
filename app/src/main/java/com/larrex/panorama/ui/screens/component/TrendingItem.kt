package com.larrex.panorama.ui.screens.component

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

private const val TAG = "TrendingItem"

@Composable
fun TrendingItem(
    imageUrl: String,
    name: String,
    description: String,
    context: Context,
    onClick: () -> Unit
) {

    val colors = listOf<Color>(Color.White, Color.Black)


    val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val display = windowManager.defaultDisplay

    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)

    val width = displayMetrics.widthPixels

    val realWidth = width / displayMetrics.density

//    Log.d(TAG, "TrendingItem: $realWidth")
    Box(
        modifier = Modifier
            .width(realWidth.dp)
            .background(Color.Black)
            .height(450.dp), contentAlignment = Alignment.BottomCenter
    ) {

        GlideImage(
            imageModel = { imageUrl },
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

        Box(modifier = Modifier.width(390.dp), Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = name,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = description,
                    modifier = Modifier
                        .padding(end = 15.dp, start = 15.dp, top = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    maxLines = 3,
                    fontFamily = Util.quicksand,
                    overflow = TextOverflow.Ellipsis

                )

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun TrendingPreview() {

//    TrendingItem("https://image.tmdb.org/t/p/w780/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg", "") {

//    }

}