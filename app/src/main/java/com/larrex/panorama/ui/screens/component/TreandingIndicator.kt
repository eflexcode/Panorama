package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Indicator
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.NavColor


@Composable
fun TrendingIndicator(

    chipSelected: Boolean = false,

    ) {

    Row(
        modifier = Modifier.padding(top = 15.dp, end = 4.dp, start = 4.dp, bottom = 10.dp)
    ) {

        Canvas(modifier = Modifier.size(5.dp)) {

            drawCircle(color = if (chipSelected) Color.White else Color.Gray,5f)

        }


    }

}