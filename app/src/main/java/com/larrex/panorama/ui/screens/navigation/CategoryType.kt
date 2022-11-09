package com.larrex.panorama.ui.screens.navigation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryType(
    var id: Int? = null,
    var name: String? = null,
    val tv: Boolean = false
): Parcelable