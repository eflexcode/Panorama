package com.larrex.panorama.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.larrex.panorama.R
import com.larrex.panorama.Util
import com.larrex.panorama.ui.theme.ChipBackground
import com.larrex.panorama.ui.theme.Green
import com.larrex.panorama.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {

    val viewModel = hiltViewModel<MainViewModel>()

    val user by viewModel.getUserDetails().collectAsState(initial = null)

//    val painter = rememberAsyncImagePainter(
//        model = user?.imageUrl,
//        placeholder = painterResource(id = R.drawable.gray),
//        error = painterResource(id = R.drawable.gray)
//    )

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    if (user != null) {

        var newText by remember { mutableStateOf(TextFieldValue(user!!.name!!)) }

        val pickImage = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->

                imageUri = uri

            }
        )

        val painter = rememberAsyncImagePainter(
            model = if (imageUri == null) user?.imageUrl else imageUri,
            placeholder = painterResource(id = R.drawable.gray),
            error = painterResource(id = R.drawable.gray)
        )

        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(top = 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "My Profile.", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 35.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Util.quicksand
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                            .toggleable(
                                value = true,
                                enabled = true,
                                role = null,
                                onValueChange = {

                                    pickImage.launch("image/*")

                                }), contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp)
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                        .background(ChipBackground, RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            value = FirebaseAuth.getInstance().currentUser?.email.toString(),
                            onValueChange = { text ->
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                contentColorFor(backgroundColor = Color.Transparent),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Color.Transparent,
                                placeholderColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            placeholder = { Text(text = "Enter email", color = Color.Gray) },
                            textStyle = TextStyle.Default.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = Util.quicksand,
                                fontSize = 16.sp,
                                color = Color.White
                            ),
                            enabled = false


                        )

                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp)
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .background(ChipBackground, RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextField(
                            value = newText,
                            onValueChange = { text ->
                                newText = text
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                contentColorFor(backgroundColor = Color.Transparent),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Color.Transparent,
                                placeholderColor = Color.Gray,
                            ),
                            singleLine = true,
                            placeholder = { Text(text = "Enter name", color = Color.Gray) },
                            textStyle = TextStyle.Default.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = Util.quicksand,
                                fontSize = 16.sp,
                                color = Color.White
                            )

                        )

                    }

                }
                Button(
                    onClick = {

                        if (newText.text.trim().isNotEmpty()) {


                            if (imageUri == null) {

                                viewModel.updateProfile(newText.text,null)

                            } else {
                                viewModel.updateProfile(newText.text, uri = imageUri)

                            }
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 20.dp)
                        .padding(bottom = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Green
                    )
                ) {

                    Text(text = "Save changes ")

                }
            }


        }
    }

}