package ru.oliverhd.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviePreview(
    val id: Int = 11005,
    val posterPath: String? = "/9gztZXuHLG6AJ0fgqGd7Q43cWRI.jpg",
    val releaseDate: String? = "1990",
    val title: String = "Awakenings",
    val rating: Double = 8.4
) : Parcelable