package com.larrex.panorama.ui.screens.navigation

sealed class NavScreens(val route: String) {

    object Movies : NavScreens("Movies")
    object TvShows : NavScreens("TvShows")
    object Search : NavScreens("Search")
    object Favorite : NavScreens("Favorite")
    object Profile : NavScreens("Profile")

}