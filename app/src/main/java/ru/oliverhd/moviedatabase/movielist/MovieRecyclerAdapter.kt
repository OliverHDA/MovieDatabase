package ru.oliverhd.moviedatabase.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.mainfragment.MainFragment
import ru.oliverhd.moviedatabase.model.MoviePreview

class MovieRecyclerAdapter(
    private var movieData: List<MoviePreview>,
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener?
) : RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_recycler_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(moviePreview: MoviePreview) {
            val imageViewMovie: ImageView = itemView.findViewById(R.id.imageViewMovie)
            imageViewMovie.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(moviePreview)
            }
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w342${moviePreview.posterPath}")
                .into(imageViewMovie)
            val textViewMovieName: TextView = itemView.findViewById(R.id.textViewMovieName)
            textViewMovieName.text = moviePreview.title
            val textViewYear: TextView = itemView.findViewById(R.id.text_view_release_year)
            textViewYear.text = moviePreview.releaseDate?.substring(0, 4)
            val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
            textViewRating.text = moviePreview.rating.toString()
        }
    }
}