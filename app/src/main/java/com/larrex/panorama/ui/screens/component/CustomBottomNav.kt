package com.larrex.panorama.ui.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.larrex.panorama.R
import com.larrex.panorama.ui.screens.navigation.NavScreens

@Composable
fun CustomBottomNav(
    profileUrl: String,
    navController: NavController,
    onClick: (route: String) -> Unit
) {


    val navItem = listOf(
        NavScreens.Movies.route,
        NavScreens.TvShows.route,
        NavScreens.Search.route,
        NavScreens.Favorite.route,
        NavScreens.Profile.route,
    )

    val navIcons = listOf(
        R.drawable.ic_movie,
        R.drawable.ic_tv_show,
        R.drawable.ic_search,
        R.drawable.ic_favorite,
        4,
    )

    val navIconsSelected = listOf(
        R.drawable.ic_movie_selected,
        R.drawable.ic_tv_show_selected,
        R.drawable.ic_search_selected,
        R.drawable.ic_favorite_selected,
        4,
    )

    val painter = rememberAsyncImagePainter(
        model = profileUrl,
        placeholder = painterResource(id = R.drawable.gray),
        error = painterResource(id = R.drawable.gray)
    )

    Surface(shadowElevation = 10.dp) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            navItem.forEachIndexed() { index, item ->

                val backStackEntry by navController.currentBackStackEntryAsState()
                val destination = backStackEntry?.destination

                val selected = destination?.hierarchy?.any { item == it.route } == true

                if (item == NavScreens.Profile.route) {

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(29.dp)
                            .clip(CircleShape)
                            .toggleable(
                                value = true,
                                enabled = true,
                                role = null,
                                onValueChange = {
                                    onClick(item)


                                }), contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                } else {

                    IconButton(onClick = {
                        onClick(item)

                    }) {

                        Icon(
                            painter = painterResource(id = if (selected) navIconsSelected[index] else navIcons[index]),
                            contentDescription = "navIcon"
                        )

                    }

                }

            }
        }

    }


}

@Preview(showBackground = false)
@Composable
fun CustomBottomNavPreview() {

//    CustomBottomNav("", true) {
//
//    }
}