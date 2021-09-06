package ru.oliverhd.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String?
) : Parcelable

fun getDefaultGenreList(): List<Genre> = listOf(getDefaultGenre())

fun getDefaultGenre(): Genre = Genre(18, "Драма")

fun getGenreLocalList(): List<Genre> = listOf(
    Genre(28, "Боевик"),
    Genre(12, "Приключения"),
    Genre(16, "Мультфильм"),
    Genre(35, "Комедия"),
    Genre(80, "Криминал"),
    Genre(99, "Документальный"),
    Genre(18, "Драма"),
    Genre(10751, "Семейный"),
    Genre(14, "Фэнтези"),
    Genre(36, "История"),
    Genre(27, "Ужасы"),
    Genre(10402, "Музыка"),
    Genre(9648, "Детектив"),
    Genre(10749, "Мелодрама"),
    Genre(878, "Фантастика"),
    Genre(10770, "Телевизионный фильм"),
    Genre(53, "Триллер"),
    Genre(10752, "Военный"),
    Genre(37, "Вестерн"),
)

fun getGenreByID(id: Int): Genre {
    val genreList: List<Genre> = getGenreLocalList()
    for (genre in genreList) {
        if (genre.id == id) {
            return genre
        }
    }
    return getDefaultGenre()
}

fun getGenreIDbyName(genreName: String?): Int {
    val genreList: List<Genre> = getGenreLocalList()
    for (genre in genreList) {
        if (genre.name == genreName) {
            return genre.id
        }
    }
    return getDefaultGenre().id
}