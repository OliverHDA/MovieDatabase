package ru.oliverhd.moviedatabase.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.moviedatabase.app.App
import ru.oliverhd.moviedatabase.app.App.Companion.getFavoriteDao
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.model.Genre
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO
import ru.oliverhd.moviedatabase.model.getGenreIDbyName
import ru.oliverhd.moviedatabase.model.getGenreLocalList
import ru.oliverhd.moviedatabase.repository.LocalRepositoryImpl
import ru.oliverhd.moviedatabase.repository.RepositoryImpl
import ru.oliverhd.moviedatabase.utils.*

class MovieListViewModel(
    private val mainLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val mainRepositoryImpl: RepositoryImpl = RepositoryImpl(RemoteDataSource()),
    private val historyRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(
        App.getHistoryDao(),
        getFavoriteDao()
    )
) : ViewModel() {

    fun getLiveData() = mainLiveData

    fun getMovieListByGenreFromRemoteSource(category: String) {
        val genreList: List<Genre> = getGenreLocalList()
        var isGenre = false
        var isHistory = false
        var isFavoriteList = false
        if (category == FAVORITE) isFavoriteList = true
        if (category == HISTORY) isHistory = true
        for (genre in genreList) {
            if (genre.name == category) {
                isGenre = true
            }
        }
        when {
            isFavoriteList -> {
                mainLiveData.postValue(
                    AppState.SuccessList(
                        historyRepositoryImpl.getAllFavorite().asReversed()
                    )
                )
            }
            isHistory -> {
                mainLiveData.postValue(
                    AppState.SuccessList(
                        historyRepositoryImpl.getAllHistory().asReversed()
                    )
                )
            }
            isGenre -> {
                mainRepositoryImpl.getMovieListByGenreFromServer(
                    getGenreIDbyName(category),
                    callBack
                )
            }
            else -> {
                mainRepositoryImpl.getMovieListBySearchQuery(category, callBack)
            }
        }
    }

    private val callBack = object :
        Callback<MoviePreviewListDTO> {

        override fun onResponse(
            call: Call<MoviePreviewListDTO>,
            response: Response<MoviePreviewListDTO>
        ) {
            val serverResponse: MoviePreviewListDTO? = response.body()
            mainLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MoviePreviewListDTO>, t: Throwable) {
            mainLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: MoviePreviewListDTO): AppState {
            return if (
                serverResponse.results == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.SuccessList(convertMoviePreviewListDtoToModel(serverResponse))
            }
        }
    }
}