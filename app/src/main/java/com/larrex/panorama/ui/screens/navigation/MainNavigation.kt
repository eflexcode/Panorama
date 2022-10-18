package com.larrex.panorama.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.larrex.panorama.ui.screens.*

@Composable
fun BottomNavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = NavScreens.Movies.route) {

      composable(NavScreens.Movies.route){
        Movies()
      }
      composable(NavScreens.TvShows.route){
        TvShows()
      }
      composable(NavScreens.Search.route){
        Search()
      }
      composable(NavScreens.Favorite.route){
       Favorite()
      }
      composable(NavScreens.Profile.route){
        Profile()
      }

    }

}