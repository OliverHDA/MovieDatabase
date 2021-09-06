package ru.oliverhd.moviedatabase.utils

import ru.oliverhd.moviedatabase.model.Genre
import ru.oliverhd.moviedatabase.model.Movie
import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview
import ru.oliverhd.moviedatabase.model.dto.GenreDTO
import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewDTO
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO
import java.text.SimpleDateFormat
import java.util.*

fun convertMovieDetailsDtoToModel(movieDetailsDTO: MovieDetailsDTO): MovieDetails {
    val genreList: MutableList<Genre> = mutableListOf()
    for (genre in movieDetailsDTO.genres) {
        genreList.add(convertGenreDtoToModel(genre))
    }
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = formatter.format(parser.parse(movieDetailsDTO.release_date))
    return MovieDetails(
        genreList,
        Movie(movieDetailsDTO.id, movieDetailsDTO.title),
        movieDetailsDTO.original_title,
        movieDetailsDTO.overview,
        movieDetailsDTO.poster_path,
        date,
        movieDetailsDTO.vote_average
    )
}

fun convertGenreDtoToModel(genreDTO: GenreDTO): Genre {
    return Genre(
        genreDTO.id,
        genreDTO.name
    )
}

fun convertMoviePreviewListDtoToModel(moviePreviewListDTO: MoviePreviewListDTO): List<MoviePreview> {
    val listOfMoviePreview: MutableList<MoviePreview> = mutableListOf()
    for (moviePreviewDTO in moviePreviewListDTO.results) {
        listOfMoviePreview.add(convertMoviePreviewDtoToModel(moviePreviewDTO))
    }
    return listOfMoviePreview
}

fun convertMoviePreviewDtoToModel(moviePreviewDTO: MoviePreviewDTO): MoviePreview {
    return MoviePreview(
        moviePreviewDTO.id,
        moviePreviewDTO.poster_path,
        moviePreviewDTO.release_date,
        moviePreviewDTO.title,
        moviePreviewDTO.vote_average
    )
}