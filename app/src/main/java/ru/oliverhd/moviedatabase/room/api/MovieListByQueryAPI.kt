package ru.oliverhd.moviedatabase.room.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO

interface MovieListByQueryAPI {

    @GET("3/search/movie")
    fun getMovieListByQuery(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean
    ): Call<MoviePreviewListDTO>
}
