package com.larrex.panorama

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.larrex.panorama.ui.screens.AuthScreen
import com.larrex.panorama.ui.screens.navigation.AuthNavigation
import com.larrex.panorama.ui.theme.PanoramaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PanoramaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navHostController = rememberNavController()
                    val auth = FirebaseAuth.getInstance().currentUser

                    AuthNavigation(
                        navHostController = navHostController,
                        application = application
                    )

                    if (auth == null) {

                        window.setFlags(
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        )

                        navHostController.navigate("auth") {
                            popUpTo("auth") {
                                inclusive = true
                            }
                        }

                    } else {

                        navHostController.navigate("home") {
                            popUpTo(0) {
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PanoramaTheme {
        Greeting("Android")
    }
}