package ru.oliverhd.moviedatabase.repository

import retrofit2.Callback
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource
import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getMovieDetailsFromServer(
        movieID: Int,
        callback: Callback<MovieDetailsDTO>
    ) {
        remoteDataSource.getMovieDetails(movieID, callback)
    }
}