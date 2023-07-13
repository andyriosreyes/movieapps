package com.andy.rios.moviesapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andy.rios.moviesapp.R
import com.andy.rios.moviesapp.databinding.FragmentFavoriteMoviesBinding
import com.andy.rios.moviesapp.ui.main.MainFragment
import com.andy.rios.moviesapp.ui.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private val viewModel: FavoriteMoviesViewModel by viewModels()
    private val movieFavoriteAdapter by lazy { MovieFavoriteAdapter() }

    private lateinit var binding: FragmentFavoriteMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setupRecyclerView()
        movieFavoriteAdapter.onItemClick = {
            val bundle = bundleOf(
                MainFragment.REQUEST_MOVIE to it, MainFragment.REQUEST_MOVIE_ID to it.id.toString()
            )
            findNavController().navigate(R.id.action_favoriteMoviesFragment_to_movieDetailFragment,bundle)
        }
    }

    private fun setupRecyclerView() = with(binding.recyclerViewFavorite) {
        adapter = movieFavoriteAdapter
    }

    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch { moviesFavorite.collect { movieFavoriteAdapter.submitData(it) } }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}