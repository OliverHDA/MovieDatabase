package ru.oliverhd.moviedatabase.utils

import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview
import ru.oliverhd.moviedatabase.model.getGenreIDbyName
import ru.oliverhd.moviedatabase.room.FavoriteEntity
import ru.oliverhd.moviedatabase.room.HistoryEntity
import java.text.SimpleDateFormat
import java.util.*

fun convertHistoryEntityToMoviePreview(entityList: List<HistoryEntity>): List<MoviePreview> {
    return entityList.map {
        getGenreIDbyName(it.genreName)
        MoviePreview(it.movieId, it.posterPath, it.releaseDate, it.title, it.rating)
    }
}

fun convertFavoriteEntityToMoviePreview(entityList: List<FavoriteEntity>): List<MoviePreview> {
    return entityList.map {
        getGenreIDbyName(it.genreName)
        MoviePreview(it.movieId, it.posterPath, it.releaseDate, it.title, it.rating)
    }
}

fun convertMovieDetailsToHistoryEntity(movieDetails: MovieDetails): HistoryEntity {

    val parser = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    val date = formatter.format(parser.parse(movieDetails.releaseDate))
    return HistoryEntity(
        0,
        movieDetails.genres[0].name,
        movieDetails.movie.id,
        movieDetails.posterPath,
        date,
        movieDetails.movie.title,
        movieDetails.rating
    )
}

fun convertMovieDetailsToFavoriteEntity(movieDetails: MovieDetails): FavoriteEntity {

    val parser = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    val date = formatter.format(parser.parse(movieDetails.releaseDate))
    val id = movieDetails.movie.id
    return FavoriteEntity(
        0,
        movieDetails.genres[0].name,
        id,
        movieDetails.posterPath,
        date,
        movieDetails.movie.title,
        movieDetails.rating
    )
}