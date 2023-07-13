package com.andy.rios.moviesapp.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieList(
    val id: Int? = 0,
    val title : String? = "",
    val overview: String? = "",
    val imageUrl: String? = "",
    val vote_average : String? = "",
    val release_date: String? = ""
): Parcelable {
}