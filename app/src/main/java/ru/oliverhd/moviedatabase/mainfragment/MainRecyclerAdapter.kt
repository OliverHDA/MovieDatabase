package ru.oliverhd.moviedatabase.mainfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.model.CategoryCard
import ru.oliverhd.moviedatabase.movielist.MovieRecyclerAdapter

class MainRecyclerAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    private var movieData: List<CategoryCard> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.textViewRecyclerSection.text = movieData[position].genre.name
        holder.movieRecycler.apply {
            layoutManager =
                LinearLayoutManager(holder.movieRecycler.context, RecyclerView.HORIZONTAL, false)
            adapter = MovieRecyclerAdapter(movieData[position].movieList, onItemViewClickListener)
        }
    }

    fun setData(data: List<CategoryCard>) {
        movieData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var movieRecycler: RecyclerView = itemView.findViewById(R.id.movieRecyclerView)
        var textViewRecyclerSection: TextView = itemView.findViewById(R.id.textViewRecyclerSection)
    }
}