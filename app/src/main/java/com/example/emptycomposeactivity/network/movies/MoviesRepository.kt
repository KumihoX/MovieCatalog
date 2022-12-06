package com.example.emptycomposeactivity.network.movies

import com.example.emptycomposeactivity.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepository {
    private val api: MoviesApi = Network.getMoviesApi()

    fun getMovies(): Flow<Movies> = flow {
        val arrMovies = api.getMovies(numberPage = 1)
        Network.movies = arrMovies
        emit(arrMovies)
    }.flowOn(Dispatchers.IO)

    fun getDetails(): Flow<Movies> = flow {
        val arrDetails = api.getDetails(movieId = "1")
        Network.movies = arrDetails
        emit(arrDetails)
    }.flowOn(Dispatchers.IO)
}