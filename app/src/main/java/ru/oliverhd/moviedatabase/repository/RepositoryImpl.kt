package ru.oliverhd.moviedatabase.repository

import retrofit2.Callback
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getMovieListByGenreFromServer(
        genreID: Int,
        callback: Callback<MoviePreviewListDTO>
    ) {
        remoteDataSource.getMoviePreviewListByGenre(genreID, callback)
    }

    override fun getMovieListBySearchQuery(query: String, callback: Callback<MoviePreviewListDTO>) {
        remoteDataSource.getMoviePreviewListByQuery(query, callback)
    }
}