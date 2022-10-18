package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.larrex.panorama.R
import com.larrex.panorama.Util

@Composable
fun TrendingItem(imageUrl: String, name: String, onClick: () -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.gray),
        error = painterResource(id = R.drawable.gray)
    )
    val colors = listOf<Color>(Color.White, Color.Black)

    Box(modifier = Modifier, contentAlignment = Alignment.Center) {

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(400.dp)
                .height(250.dp)
                .padding(start = 3.dp, end = 3.dp, top = 3.dp)
                .clip(RoundedCornerShape(20.dp))
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

                    }), contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Text(
            text = name, modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = Util.quicksand,
            maxLines = 2

        )

    }
}

@Preview(showBackground = true)
@Composable
fun TrendingPreview() {

    TrendingItem("https://image.tmdb.org/t/p/w780/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg", "") {

    }

}