package com.larrex.panorama.ui.screens

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.larrex.panorama.R
import com.larrex.panorama.ui.theme.Blue
import com.larrex.panorama.ui.viewmodel.MainViewModel


@Composable
fun AuthScreen(application: Application) {

    val colors = listOf<Color>(Color.White, Color.Black)

    val googleSignInOptions: GoogleSignInOptions =
        GoogleSignInOptions.Builder()
            .requestEmail()
            .requestProfile()
            .requestIdToken(stringResource(id = R.string.default_web_client_id))
            .build()

    val googleClient: GoogleSignInClient = GoogleSignIn.getClient(application, googleSignInOptions)

    val viewModel = hiltViewModel<MainViewModel>()

    val startActivityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == RESULT_OK) {

            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            val googleSignInAccount = task.getResult(ApiException::class.java)

            val authCredential: AuthCredential =
                GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

            viewModel.doGoogleAuth(authCredential)

        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors)),
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = painterResource(id = R.drawable.login_background3),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {

                    val gradient = Brush.verticalGradient(colors)

                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,

            )

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val quicksand = FontFamily(

                Font(R.font.quicksand_regular, FontWeight.Normal),
                Font(R.font.quicksand_medium, FontWeight.Medium),
                Font(R.font.quicksand_bold, FontWeight.Bold)

            )

            Text(
                text = "Panorama.", modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = quicksand

            )

            Text(
                text = "Watch your favorite movies or series on only one platform. You can watch it anytime and anywhere.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp, start = 40.dp, top = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontFamily = quicksand

            )

            Button(
                onClick = {

                    val intent = googleClient.signInIntent

                    startActivityResultLauncher.launch(intent)

                },
                modifier = Modifier
                    .padding(bottom = 70.dp, top = 30.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(Blue)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Color.White
                )

                Text(
                    text = "Sign in with Google",
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand,
                    color = Color.White
                )

            }

        }

    }

}