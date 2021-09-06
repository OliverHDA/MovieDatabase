package ru.oliverhd.moviedatabase.repository

import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO

interface DetailsRepository {

    fun getMovieDetailsFromServer(
        movieID: Int,
        callback: retrofit2.Callback<MovieDetailsDTO>
    )
}