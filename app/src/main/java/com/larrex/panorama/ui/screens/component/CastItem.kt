package com.larrex.panorama.ui.screens.component

import android.graphics.drawable.Drawable
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.theme.Green
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.ImageComponent
import com.skydoves.landscapist.components.imageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@Composable
fun CastItem(imageUrl: String,name :String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp)
            .padding(3.dp),
        contentAlignment = Alignment.TopEnd
    ) {

        GlideImage(
            imageModel = { imageUrl },
            modifier = Modifier
                .fillMaxSize()

                .clip(RoundedCornerShape(10.dp))
                .toggleable(
                    value = true,
                    enabled = true,
                    role = null,
                    onValueChange = {

                        onClick()

                    }), component = imageComponent {

                +PlaceholderPlugin.Loading(painterResource(id = R.drawable.gray))
                +PlaceholderPlugin.Failure(painterResource(id = R.drawable.gray))
                +CrossfadePlugin(duration = 350)

            }

        )



    }
}

@Preview(showBackground = true)
@Composable
fun CastPre() {

    CastItem(imageUrl = "","Eze Larry Ifeanyi") {

    }

}