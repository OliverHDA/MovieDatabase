package ru.oliverhd.moviedatabase.repository

import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview

interface LocalRepository {
    fun getAllHistory(): List<MoviePreview>
    fun saveHistory(movie: MovieDetails)
    fun getAllFavorite(): List<MoviePreview>
    fun saveFavorite(movie: MovieDetails)
    fun checkFavorite(movieId: Int): Boolean
    fun deleteFavorite(movieId: Int)
}
