package ru.oliverhd.moviedatabase.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movie_id"], unique = true)])
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val genreName: String? = "",
    @ColumnInfo(name = "movie_id")
    val movieId: Int = 0,
    val posterPath: String? = "poster_path",
    val releaseDate: String = "1990",
    val title: String = "Awakenings",
    val rating: Double = 0.0
)
