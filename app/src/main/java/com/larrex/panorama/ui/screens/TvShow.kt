package com.larrex.panorama.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larrex.panorama.Util
import com.larrex.panorama.ui.screens.component.ProviderChip

@Composable
fun TvShows() {

    val listOfChips = listOf(
        "Any",
        "Amazon Prime Video",
        "Apple iTunes",
        "Disney+",
        "Discovery+",
        "Hulu",
        "HBO Max",
        "Netflix",
        "Google Play Movies"
    )

    var selected by remember() {
        mutableStateOf("Any")
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        LazyColumn() {

            item {

                Text(
                    text = "Tv Shows.", modifier = Modifier.fillMaxWidth() .padding(start = 20.dp, top = 35.dp),
                    textAlign = TextAlign.Start,
                    fontSize =25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand

                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentPadding = PaddingValues(start = 10.dp, end = 10.dp)

                ) {
                    items(listOfChips) {

                        ProviderChip(
                            it,
                            chipSelected = it == selected,
                            onChipSelected = {

                                selected = it

                            }, modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(30.dp))
                        )
                    }
                }

            }
        }

    }

}