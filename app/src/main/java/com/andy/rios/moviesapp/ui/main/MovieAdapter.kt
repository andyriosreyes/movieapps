package com.andy.rios.moviesapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andy.rios.moviesapp.databinding.ItemMovieBinding
import com.andy.rios.moviesapp.ui.model.MovieList
import com.andy.rios.moviesapp.ui.util.DiffCallback
import com.andy.rios.moviesapp.ui.util.simpleDateFormat
import com.bumptech.glide.Glide


class MovieAdapter(
) : PagingDataAdapter<MovieList, MovieAdapter.HolderMovies>(
    DiffCallback<MovieList>(
        { old, new -> old == new },
        { old, new -> old == new })
) {

    var onItemClick: (item: MovieList) -> Unit = { _ -> }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onItemClick)
    }

    @Suppress("TooGenericExceptionThrown")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    class HolderMovies(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: MovieList?, onItemClick: (MovieList) -> Unit) {
            loadImage(binding.image, item?.imageUrl ?: "")
            binding.tvTitle.text = item?.title
            binding.tvDate.text = setDate(item?.release_date?:"1990-01-01")
            binding.tvRate.text = item?.vote_average

            binding.lyCardMovie.setOnClickListener {
                if (item != null) {
                    onItemClick.invoke(item)
                }
            }
        }

        private fun loadImage(image: AppCompatImageView, url: String) = Glide.with(image)
            .load(url)
//        .placeholder(R.color.light_gray)
//        .error(R.drawable.bg_image)
            .into(image)

        private fun setDate(dateMovie: String): String {
            var dateMovies : String = dateMovie
            if(dateMovie.isEmpty()){
                dateMovies = "1990-01-01"
            }
            val date = simpleDateFormat(dateMovies, "yyyy-MM-dd")
            val dayOfWeek = android.text.format.DateFormat.format("EEEE", date)
            val day = android.text.format.DateFormat.format("d", date)
            val monthString = android.text.format.DateFormat.format("MMMM", date)
            val year = android.text.format.DateFormat.format("yyyy", date)
            return "$day de $monthString del $year"
        }

    }
}


