package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larrex.panorama.Util
import com.larrex.panorama.R

@Composable
fun CategoryItem(categoryName: String, id: Int) {

    Box(modifier = Modifier.fillMaxWidth()) {

        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = categoryName,
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 40.dp, end = 10.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand,
                    maxLines = 2,

                    )

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.3f)) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = null
                    )

                }

            }

            LazyRow() {


            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    CategoryItem("", 0)
}