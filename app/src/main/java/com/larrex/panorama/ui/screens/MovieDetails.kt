package com.larrex.panorama.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larrex.panorama.R
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
                onClick = { /*TODO*/ }, modifier = Modifier.background(
                    Color.Black.copy(0.6f),
                    CircleShape
                ).size(70.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )

            }

        }

//        IconButton(
//            onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 10.dp, top = 35.dp).background(
//                Color.Black.copy(0.6f),
//                CircleShape
//            )
//        ) {

//            Icon(
//                painter = painterResource(id = R.drawable.ic_cancel),
//                        modifier = Modifier.size(30.dp).padding(4.dp).background(
//                Color.Black.copy(0.6f),
//                RoundedCornerShape(70.dp)
//                        ),
//                contentDescription = null,
//                tint = Color.White,
//
//            )

//        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieDetailsPre() {
    MovieDetails()
}