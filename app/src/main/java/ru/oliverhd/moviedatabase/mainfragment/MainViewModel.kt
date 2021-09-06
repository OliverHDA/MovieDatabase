package ru.oliverhd.moviedatabase.mainfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.model.*
import ru.oliverhd.moviedatabase.model.dto.MoviePreviewListDTO
import ru.oliverhd.moviedatabase.repository.*
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource
import ru.oliverhd.moviedatabase.utils.convertMoviePreviewListDtoToModel
import ru.oliverhd.moviedatabase.utils.SERVER_ERROR
import ru.oliverhd.moviedatabase.utils.REQUEST_ERROR
import ru.oliverhd.moviedatabase.utils.CORRUPTED_DATA

class MainViewModel(
    private val mainLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val mainRepositoryImpl: RepositoryImpl = RepositoryImpl(RemoteDataSource())
) : ViewModel() {

    val categoryCardList: MutableList<CategoryCard> = mutableListOf()

    fun getLiveData() = mainLiveData

    fun getMovieListByGenreFromRemoteSource() {
        mainLiveData.value = AppState.Loading
        val genreList: List<Genre> = getGenreLocalList()
        for (genre in genreList) {
            mainRepositoryImpl.getMovieListByGenreFromServer(genre.id, callBack)
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
            return if (serverResponse.id == null || serverResponse.page == null || serverResponse.results == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                categoryCardList.add(
                    CategoryCard(
                        getGenreByID(serverResponse.id),
                        convertMoviePreviewListDtoToModel(serverResponse)
                    )
                )
                AppState.Success(categoryCardList)
            }
        }
    }
}
