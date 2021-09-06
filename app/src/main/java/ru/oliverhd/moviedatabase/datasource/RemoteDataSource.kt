package ru.oliverhd.moviedatabase.datasource

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.oliverhd.moviedatabase.BuildConfig
import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO
import ru.oliverhd.moviedatabase.room.api.MovieDetailsAPI
import ru.oliverhd.moviedatabase.room.api.MovieListByGenreAPI
import ru.oliverhd.moviedatabase.room.api.MovieListByQueryAPI
import ru.oliverhd.moviedatabase.utils.BASE_URL
import ru.oliverhd.moviedatabase.utils.LANGUAGE_RU
import java.io.IOException

class RemoteDataSource {

    private val movieDetailsAPI: MovieDetailsAPI =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(MovieInfoApiInterceptor()))
            .build()
            .create(MovieDetailsAPI::class.java)

    fun getMovieDetails(movieID: Int, callback: Callback<MovieDetailsDTO>) {
        movieDetailsAPI.getMovieInfo(movieID, BuildConfig.MOVIE_API_KEY, LANGUAGE_RU)
            .enqueue(callback)
    }

    private val movieListByGenreAPI: MovieListByGenreAPI =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(MovieInfoApiInterceptor()))
            .build()
            .create(MovieListByGenreAPI::class.java)

    fun getMoviePreviewListByGenre(genreID: Int, callback: Callback<MoviePreviewListDTO>) {
        movieListByGenreAPI.getMovieListByGenre(
            genreID, BuildConfig.MOVIE_API_KEY, LANGUAGE_RU
        ).enqueue(callback)
    }

    private val movieListByQueryAPI: MovieListByQueryAPI =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(MovieInfoApiInterceptor()))
            .build()
            .create(MovieListByQueryAPI::class.java)

    fun getMoviePreviewListByQuery(query: String, callback: Callback<MoviePreviewListDTO>) {
        movieListByQueryAPI.getMovieListByQuery(
            BuildConfig.MOVIE_API_KEY,
            LANGUAGE_RU,
            query,
            1,
            isAdultOn
        ).enqueue(callback)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    companion object {
        var isAdultOn: Boolean = true
    }

    inner class MovieInfoApiInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}