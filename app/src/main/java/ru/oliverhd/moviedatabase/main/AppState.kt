package ru.oliverhd.moviedatabase.main

import ru.oliverhd.moviedatabase.model.CategoryCard
import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview

sealed class AppState {
    data class SuccessList(val movieData: List<MoviePreview>) : AppState()
    data class SuccessDetail(val movieData: MovieDetails) : AppState()
    data class Success(val movieData: List<CategoryCard>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}