package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
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
fun MovieItem(tv: Boolean, imageUrl: String, onClick: () -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.gray),
        error = painterResource(id = R.drawable.gray)
    )

    Box(modifier = Modifier) {

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(140.dp)
                .height(200.dp)
                .padding(start = 3.dp, end = 3.dp, top = 3.dp)
                .clip(RoundedCornerShape(10.dp))
                .toggleable(
                    value = true,
                    enabled = true,
                    role = null,
                    onValueChange = {

                        onClick()

                    }), contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

    }
}

@Preview(showBackground = false)
@Composable
fun MoviesPre() {

    MovieItem(tv = false, imageUrl = "") {

    }

}