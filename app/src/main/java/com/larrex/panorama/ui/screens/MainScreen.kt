package com.larrex.panorama.ui.screens

import android.app.Application
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.larrex.panorama.ui.screens.component.CustomBottomNav
import com.larrex.panorama.ui.screens.navigation.BottomNavGraph
import com.larrex.panorama.ui.screens.navigation.NavScreens
import com.larrex.panorama.ui.theme.Typography

;

private const val TAG = "MainScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(application: Application) {

    val uiControl = rememberSystemUiController()
    val navController = rememberNavController()

    if (isSystemInDarkTheme()) {
        uiControl.setSystemBarsColor(Color.Black)
    } else {
        uiControl.setSystemBarsColor(Color.White)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(bottomBar = {

            val isNavBarVisible =
                currentDestination?.route != NavScreens.TvDetails.route && currentDestination?.route != NavScreens.MovieDetails.route

            AnimatedVisibility(
                visible = isNavBarVisible,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                CustomBottomNav(
                    navController
                ) {

                    Log.d(TAG, "MainScreen: $it")

                    navController.navigate(it) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true

                    }

                }
            }
        }) {
            it
            BottomNavGraph(navHostController = navController, application = application)

        }


    }
}

