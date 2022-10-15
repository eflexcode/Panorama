package com.larrex.panorama.ui.screens.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.larrex.panorama.ui.screens.AuthScreen
import com.larrex.panorama.ui.screens.MainScreen

@Composable
fun AuthNavigation(navHostController: NavHostController, application: Application) {


    NavHost(navController = navHostController, startDestination = "auth") {

        composable("auth") {
            AuthScreen(application = application, navHostController)
        }
        composable("home") {
            MainScreen()
        }

    }

}