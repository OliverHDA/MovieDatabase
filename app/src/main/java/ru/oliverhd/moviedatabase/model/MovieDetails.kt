package ru.oliverhd.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetails(
    val genres: List<Genre> = getDefaultGenreList(),
    val movie: Movie = getDefaultMovie(),
    val originalTitle: String = "Original Title",
    val overview: String? = "Overview",
    val posterPath: String? = "poster_path",
    val releaseDate: String = "1990",
    val rating: Double = 0.0
) : Parcelable

fun getDefaultMovie() =
    Movie(
        11005,
        "Awakenings"
    )