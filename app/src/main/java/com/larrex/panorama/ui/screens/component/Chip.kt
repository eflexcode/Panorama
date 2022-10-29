package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
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
import com.larrex.panorama.Util
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.NavColor


@Composable
fun ProviderChip(
    chipText: String,
    chipSelected: Boolean = false,
    onChipSelected: (String) -> Unit,
    modifier: Modifier =
        Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(30.dp))
) {

    Surface(
        modifier = modifier,
        color = if (chipSelected) Color.White else ChipBackground,

        ) {

        Row(modifier = Modifier
            .toggleable(value = chipSelected, onValueChange = {
                onChipSelected(chipText)
            })
            .padding(top = 10.dp, end = 20.dp, start = 20.dp, bottom = 10.dp)) {

            Text(
                text = chipText,
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = if (chipSelected) Color.Black else Color.Gray,
                fontFamily = Util.quicksand
            )

        }

    }

}