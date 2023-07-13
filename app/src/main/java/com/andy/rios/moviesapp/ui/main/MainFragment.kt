package com.andy.rios.moviesapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.andy.rios.moviesapp.R
import com.andy.rios.moviesapp.databinding.FragmentMainBinding
import com.andy.rios.moviesapp.ui.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        const val REQUEST_MOVIE = "REQUEST_MOVIE"
        const val REQUEST_MOVIE_ID = "REQUEST_MOVIE_ID"
    }

    private val viewModel: MoviesViewModel by viewModels()
    private val viewSearchModel: MoviesSearchViewModel by viewModels()

    private val movieAdapter by lazy { MovieAdapter() }

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        observeViewModel()
        observeViewSearchModel()
    }

    private fun setupViews() {
        setupRecyclerView()
        movieAdapter.onItemClick = {
            val bundle = bundleOf(REQUEST_MOVIE to it,REQUEST_MOVIE_ID to it.id.toString()
            )
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment,bundle)
        }
        setupSearch()
    }

    private fun setupSearch(){
        binding.txtSearch.doAfterTextChanged {
            viewSearchModel.onSearchQuery(it.toString())
        }
    }

    private fun observeViewModel() = with(viewModel) {
        launchAndRepeatWithViewLifecycle {
            launch {
                movies.collect {
//                    movieAdapter.submitData(PagingData.empty())
                    movieAdapter.submitData(it) }
            }
        }
    }

    private fun observeViewSearchModel() = with(viewSearchModel) {
        launchAndRepeatWithViewLifecycle {
            launch {
                moviesSearch.observe(viewLifecycleOwner){
                        movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        adapter = movieAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}