package ru.oliverhd.moviedatabase.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.databinding.FragmentMovieDetailBinding
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.model.MovieDetails
import ru.oliverhd.moviedatabase.model.MoviePreview
import ru.oliverhd.moviedatabase.utils.showSnackBar

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieDetailsBundle: MoviePreview
    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this).get(
            MovieDetailViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: MoviePreview()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getMovieInfoFromRemoteSource(movieDetailsBundle.id)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessDetail -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingMovieDetailLayout.visibility = View.GONE
                setMovieDetail(appState.movieData)
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingMovieDetailLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingMovieDetailLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieInfoFromRemoteSource(
                            movieDetailsBundle.id
                        )
                    })
            }
        }
    }

    private fun setMovieDetail(movieDetails: MovieDetails) {
        with(binding) {

            textViewMovieName.text = movieDetailsBundle.title
            var genres: String = "${textViewGenre.text.toString()}: "
            for (genre in movieDetails.genres) {
                genres = if (genre == movieDetails.genres.last()) {
                    "$genres${genre.name}."
                } else {
                    "$genres${genre.name}, "
                }

            }
            textViewGenre.text = genres
            textViewRating.text = movieDetails.rating.toString()

            val date: String = "${textViewReleaseDate.text.toString()}: ${movieDetails.releaseDate}"
            textViewReleaseDate.text = date

            textViewOverview.text = movieDetails.overview
            if (viewModel.checkFavorite(movieDetails.movie.id)) {
                favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)

            } else {
                favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            favoriteImageView.setOnClickListener {
                if (viewModel.checkFavorite(movieDetails.movie.id)) {
                    viewModel.deleteFavorite(movieDetails.movie.id)
                    favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    viewModel.saveFavorite(movieDetails)
                    favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            }
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w780${movieDetails.posterPath}")
                .into(imageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val BUNDLE_EXTRA = "movieInfo"

        fun newInstance(bundle: Bundle): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}