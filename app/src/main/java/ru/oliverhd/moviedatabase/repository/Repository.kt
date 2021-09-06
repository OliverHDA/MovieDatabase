package ru.oliverhd.moviedatabase.repository

import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO

interface Repository {

    fun getMovieListByGenreFromServer(
        genreID: Int,
        callback: retrofit2.Callback<MoviePreviewListDTO>
    )

    fun getMovieListBySearchQuery(
        query: String,
        callback: retrofit2.Callback<MoviePreviewListDTO>
    )
}