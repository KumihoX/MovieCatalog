package com.example.emptycomposeactivity.network.movies

@kotlinx.serialization.Serializable
data class Movies(
    val movies: Array<MoviesDescription>,
    val pageInfo: MoviesPage
)
