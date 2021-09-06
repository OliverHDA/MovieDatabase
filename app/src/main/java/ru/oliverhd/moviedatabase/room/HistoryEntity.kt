package ru.oliverhd.moviedatabase.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val genreName: String? = "",
    val movieId: Int = 0,
    val posterPath: String? = "poster_path",
    val releaseDate: String = "1990",
    val title: String = "Awakenings",
    val rating: Double = 0.0
)