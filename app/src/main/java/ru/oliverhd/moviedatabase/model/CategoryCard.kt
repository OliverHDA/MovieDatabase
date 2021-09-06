package ru.oliverhd.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryCard(
    val genre: Genre = getDefaultGenre(),
    val movieList: List<MoviePreview> = getDefaultMovieList()
) : Parcelable

fun getDefaultMovieList(): List<MoviePreview> = listOf(
    MoviePreview(
        11005,
        "/9gztZXuHLG6AJ0fgqGd7Q43cWRI.jpg",
        "1990-12-04",
        "Пробуждение",
        8.4
    )
)

fun getDefaultGenreIDs(): List<Int> = listOf(18)