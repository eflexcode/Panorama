package com.larrex.panorama.ui.screens.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.larrex.panorama.ui.screens.*

@Composable
fun BottomNavGraph(navHostController: NavHostController,application: Application) {

    val uiControl = rememberSystemUiController()

    uiControl.setSystemBarsColor(Color.Black)

    NavHost(navController = navHostController, startDestination = NavScreens.Movies.route) {

        composable(NavScreens.Movies.route) {
            Movies(navHostController,application)
        }
        composable(NavScreens.TvShows.route) {
            TvShows(navHostController)
        }
        composable(NavScreens.Search.route) {
            Search()
        }
        composable(NavScreens.Favorite.route) {
            Favorite()
        }
        composable(NavScreens.Profile.route) {
            Profile()
        }
        composable(NavScreens.MovieDetails.route) {

            val id = navHostController.previousBackStackEntry?.savedStateHandle?.get<String>("id")

            if (id != null) {
                MovieDetails(id)
            }
        }
        composable(NavScreens.TvDetails.route) {

            val id = navHostController.previousBackStackEntry?.savedStateHandle?.get<String>("tvId")

            if (id != null) {
                TvDetails(id)
            }
        }
        composable(NavScreens.MovieDetails.route) {

            val type = navHostController.previousBackStackEntry?.savedStateHandle?.get<CategoryType>("type")

            if (type != null) {
                MovieGenre(navHostController,type)
            }
        }

    }

}