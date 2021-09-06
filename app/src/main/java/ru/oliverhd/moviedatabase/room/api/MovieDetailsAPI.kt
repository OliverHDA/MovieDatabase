package ru.oliverhd.moviedatabase.room.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO

interface MovieDetailsAPI {

    @GET("3/movie/{MOVIE_ID}")
    fun getMovieInfo(
        @Path("MOVIE_ID") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieDetailsDTO>
}