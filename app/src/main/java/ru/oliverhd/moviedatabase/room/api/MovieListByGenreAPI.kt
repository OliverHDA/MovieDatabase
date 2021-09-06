package ru.oliverhd.moviedatabase.room.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO

interface MovieListByGenreAPI {

    @GET("3/genre/{GENRE_ID}/movies")
    fun getMovieListByGenre(
        @Path("GENRE_ID") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MoviePreviewListDTO>
}