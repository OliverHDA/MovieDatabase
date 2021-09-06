package ru.oliverhd.moviedatabase.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.databinding.FragmentMovieListBinding
import ru.oliverhd.moviedatabase.detail.MovieDetailFragment
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.mainfragment.MainFragment
import ru.oliverhd.moviedatabase.model.MoviePreview
import ru.oliverhd.moviedatabase.utils.showSnackBar

private const val ARG_CATEGORY = "category"

class MovieListFragment : Fragment() {

    private val category: String by lazy {
        arguments?.getString(ARG_CATEGORY).orEmpty()
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var data: List<MoviePreview> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieListByGenreFromRemoteSource(category)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessList -> {
                binding.movieListFragmentLoadingLayout.visibility = View.GONE
                data = appState.movieData
                binding.categoryTextView.text = category
                initRecycler()
            }
            is AppState.Loading -> {
                binding.movieListFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.movieListFragmentLoadingLayout.visibility = View.GONE
                binding.movieListFragmentView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieListByGenreFromRemoteSource(category)
                    })

                Snackbar
                    .make(
                        binding.movieListFragmentView,
                        getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) {
                        viewModel.getMovieListByGenreFromRemoteSource(category)
                    }
                    .show()
            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieRecyclerAdapter(data, object : MainFragment.OnItemViewClickListener {
            override fun onItemViewClick(moviePreview: MoviePreview) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(MovieDetailFragment.BUNDLE_EXTRA, moviePreview)
                    manager.beginTransaction()
                        .replace(R.id.flFragment, MovieDetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

        recyclerView = binding.movieListRecyclerView

        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }
    }

    companion object {
        fun newInstance(category: String) =
            MovieListFragment().apply {
                arguments = bundleOf(ARG_CATEGORY to category)
            }
    }
}