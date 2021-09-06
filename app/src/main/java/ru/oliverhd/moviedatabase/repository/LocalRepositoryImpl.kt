package ru.oliverhd.moviedatabase.repository

import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview
import ru.oliverhd.moviedatabase.room.FavoriteDao
import ru.oliverhd.moviedatabase.room.FavoriteEntity
import ru.oliverhd.moviedatabase.room.HistoryDao
import ru.oliverhd.moviedatabase.room.HistoryEntity
import ru.oliverhd.moviedatabase.utils.convertFavoriteEntityToMoviePreview
import ru.oliverhd.moviedatabase.utils.convertHistoryEntityToMoviePreview
import ru.oliverhd.moviedatabase.utils.convertMovieDetailsToFavoriteEntity
import ru.oliverhd.moviedatabase.utils.convertMovieDetailsToHistoryEntity

class LocalRepositoryImpl(private val history: HistoryDao, private val favorite: FavoriteDao) :
    LocalRepository {

    override fun getAllHistory(): List<MoviePreview> {
        val entityList: List<HistoryEntity> = history.allHistory()
        return convertHistoryEntityToMoviePreview(entityList)
    }

    override fun saveHistory(movie: MovieDetails) {
        val entity: HistoryEntity = convertMovieDetailsToHistoryEntity(movie)
        history.insertHistory(entity)
    }

    override fun getAllFavorite(): List<MoviePreview> {
        val entityList: List<FavoriteEntity> = favorite.allFavorite()
        return convertFavoriteEntityToMoviePreview(entityList)
    }

    override fun saveFavorite(movie: MovieDetails) {
        val entity: FavoriteEntity = convertMovieDetailsToFavoriteEntity(movie)
        favorite.insertFavorite(entity)
    }

    override fun checkFavorite(movieId: Int): Boolean {
        return favorite.checkFavorite(movieId)
    }

    override fun deleteFavorite(movieId: Int) {
        favorite.deleteByMovieId(movieId)
    }
}
