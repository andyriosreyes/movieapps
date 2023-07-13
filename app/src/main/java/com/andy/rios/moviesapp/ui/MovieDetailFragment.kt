package com.andy.rios.moviesapp.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.andy.rios.moviesapp.R
import com.andy.rios.moviesapp.databinding.FragmentMovieDetailBinding
import com.andy.rios.moviesapp.ui.main.MoviesViewModel
import com.andy.rios.moviesapp.ui.model.MovieList
import com.andy.rios.moviesapp.ui.util.launchAndRepeatWithViewLifecycle
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    companion object {
        const val REQUEST_MOVIE = "REQUEST_MOVIE"
        const val REQUEST_MOVIE_ID = "REQUEST_MOVIE_ID"
    }

    @Inject
    lateinit var factory: MovieDetailViewModel.Factory

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModel.provideFactory(factory, arguments?.getString(REQUEST_MOVIE_ID)?.toInt()?:0)
    }

    private var movieList = MovieList()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arguments?.getParcelable<MovieList>(REQUEST_MOVIE)
        if (data != null) {
            movieList = data
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() = with(binding) {
        favorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            uiState.collect { showData(it) }
        }
    }

    private fun getFavoriteDrawable(favorite: Boolean): Drawable? = if (favorite) {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_fill_white_48)
    } else {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border_white_48)
    }

    private fun updateFavoriteDrawable(drawable: Drawable?) = with(binding.favorite) {
        setImageDrawable(drawable)
    }

    private fun showData(movie: MovieDetailViewModel.MovieDetailsUiState) {
        binding.tvDetailTitle.text = movie.title
        binding.tvDetailDescription.text = movie.overview
        loadImage(movie.backdrop_path?:"")
        updateFavoriteDrawable(getFavoriteDrawable(movie.isFavorite))
    }

    private fun loadImage(url: String) =
        Glide.with(this).load(url).error(R.drawable.ic_launcher_background).into(binding.ivDetailImage)
}