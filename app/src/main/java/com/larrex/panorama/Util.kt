package com.larrex.panorama

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.util.*

class Util {

    companion object {

        const val USER_COLLECTION = "Users"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "ea2c24d1075046490884393681831ff8"

        val quicksand = FontFamily(

            Font(R.font.quicksand_regular, FontWeight.Normal),
            Font(R.font.quicksand_medium, FontWeight.Medium),
            Font(R.font.quicksand_bold, FontWeight.Bold)

        )

        fun getGreeting(): String {

            val time = Date().hours

            return if (time < 12) {
                "Good morning,"
            } else if (time < 16) {
                "Good afternoon ,"
            } else if (time < 18) {
                "Good evening,"
            } else {
                "Good night,"
            }

        }
    }

}