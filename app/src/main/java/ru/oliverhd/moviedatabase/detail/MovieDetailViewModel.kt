package ru.oliverhd.moviedatabase.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.moviedatabase.app.App.Companion.getFavoriteDao
import ru.oliverhd.moviedatabase.app.App.Companion.getHistoryDao
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.dto.MovieDetailsDTO
import ru.oliverhd.moviedatabase.repository.DetailsRepository
import ru.oliverhd.moviedatabase.repository.DetailsRepositoryImpl
import ru.oliverhd.moviedatabase.repository.LocalRepositoryImpl
import ru.oliverhd.moviedatabase.utils.CORRUPTED_DATA
import ru.oliverhd.moviedatabase.utils.REQUEST_ERROR
import ru.oliverhd.moviedatabase.utils.SERVER_ERROR
import ru.oliverhd.moviedatabase.utils.convertMovieDetailsDtoToModel

class MovieDetailViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val localRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(
        getHistoryDao(),
        getFavoriteDao()
    )
) : ViewModel() {

    fun getLiveData() = detailsLiveData

    fun getMovieInfoFromRemoteSource(movieID: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(movieID, callBack)
    }

    fun checkFavorite(movieID: Int): Boolean {
        return localRepositoryImpl.checkFavorite(movieID)
    }

    fun saveFavorite(movieDetails: MovieDetails) {
        localRepositoryImpl.saveFavorite(movieDetails)
    }

    fun deleteFavorite(movieID: Int) {
        localRepositoryImpl.deleteFavorite(movieID)
    }

    private val callBack = object :
        Callback<MovieDetailsDTO> {

        override fun onResponse(call: Call<MovieDetailsDTO>, response: Response<MovieDetailsDTO>) {
            val serverResponse: MovieDetailsDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDetailsDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: MovieDetailsDTO): AppState {
            return if (serverResponse.overview.isNullOrEmpty() || serverResponse.poster_path.isNullOrEmpty() || serverResponse.release_date.isNullOrEmpty()) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                with(convertMovieDetailsDtoToModel(serverResponse)) {
                    localRepositoryImpl.saveHistory(this)
                }
                AppState.SuccessDetail(convertMovieDetailsDtoToModel(serverResponse))
            }
        }
    }
}