package co.krishna.movieapp.api

import co.krishna.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): MovieResponse
}