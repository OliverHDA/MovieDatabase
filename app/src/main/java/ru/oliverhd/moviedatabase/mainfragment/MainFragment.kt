package ru.oliverhd.moviedatabase.mainfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.databinding.FragmentMainBinding
import ru.oliverhd.moviedatabase.detail.MovieDetailFragment
import ru.oliverhd.moviedatabase.main.AppState
import ru.oliverhd.moviedatabase.model.MoviePreview

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        binding.mainFragmentRecyclerView.adapter = mainAdapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieListByGenreFromRemoteSource()
    }

    override fun onDestroy() {
        mainAdapter.removeListener()
        super.onDestroy()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                mainAdapter.setData(appState.movieData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieListByGenreFromRemoteSource()
                    })

                Snackbar
                    .make(
                        binding.mainFragmentRootView,
                        getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getMovieListByGenreFromRemoteSource() }
                    .show()
            }
        }
    }

    private fun initRecycler() {
        mainAdapter = MainRecyclerAdapter(object : OnItemViewClickListener {
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

        recyclerView = binding.mainFragmentRecyclerView

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = mainAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(moviePreview: MoviePreview)
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}