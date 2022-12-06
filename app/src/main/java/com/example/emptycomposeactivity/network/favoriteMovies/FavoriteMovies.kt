package com.example.emptycomposeactivity.network.favoriteMovies

@kotlinx.serialization.Serializable
data class FavoriteMovies(
    val movies: Array<ShortMovie>
)
