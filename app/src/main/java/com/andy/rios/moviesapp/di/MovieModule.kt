package com.andy.rios.moviesapp.di

import com.andy.rios.moviesapp.domain.repository.MovieRepository
import com.andy.rios.moviesapp.domain.usecase.GetMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MovieModule {

}