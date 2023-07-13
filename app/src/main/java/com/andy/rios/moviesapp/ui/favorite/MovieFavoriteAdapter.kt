package com.andy.rios.moviesapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andy.rios.moviesapp.databinding.ItemMovieFavoriteBinding
import com.andy.rios.moviesapp.ui.model.MovieList
import com.andy.rios.moviesapp.ui.util.DiffCallback
import com.bumptech.glide.Glide


class MovieFavoriteAdapter(
) : PagingDataAdapter<MovieList, MovieFavoriteAdapter.HolderMovies>(
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
            ItemMovieFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    class HolderMovies(val binding: ItemMovieFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: MovieList?, onItemClick: (MovieList) -> Unit) {
            loadImage(binding.image, item?.imageUrl ?: "")
            binding.tvTitleFavorite.text = item?.title
            binding.lyCardMovieFavorite.setOnClickListener {
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

    }
}


