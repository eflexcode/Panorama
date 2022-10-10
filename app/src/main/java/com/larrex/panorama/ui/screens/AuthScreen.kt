package com.larrex.panorama.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larrex.panorama.R
import com.larrex.panorama.ui.theme.Blue


@Composable
fun AuthScreen() {

    val colors = listOf<Color>(Color.White, Color.Black)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors)),
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = painterResource(id = R.drawable.login_background3),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {

                    val gradient = Brush.verticalGradient(colors)

                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,

            )

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val quicksand = FontFamily(

                Font(R.font.quicksand_regular, FontWeight.Normal),
                Font(R.font.quicksand_medium, FontWeight.Medium),
                Font(R.font.quicksand_bold, FontWeight.Bold)

            )

            Text(
                text = "Panorama.", modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = quicksand

            )

            Text(
                text = "Watch your favorite movies or series on only one platform. You can watch it anytime and anywhere.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp, start = 40.dp, top = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontFamily = quicksand

            )

            Button(onClick = { },
                modifier = Modifier.padding(bottom = 70.dp, top = 30.dp, start = 30.dp, end = 30.dp).fillMaxWidth()
                    .height(60.dp),
            colors = ButtonDefaults.buttonColors(Blue)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Color.White
                )

                Text(text = "Sign in with Google",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand,
                    color = Color.White)

            }

        }

    }

}