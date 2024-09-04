package co.krishna.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.krishna.movieapp.model.Movie
import co.krishna.movieapp.utils.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    init {
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTrendingMovies("en-US","3f56d57a46c3452acd8682959a974088")
                _movies.value = response.results
                Log.d("MovieViewModel", "Fetched movies: ${response.results}")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = _movies.value.find { it.id == movieId }
                if (movie != null) {
                    _selectedMovie.value = movie
                } else {

                    _selectedMovie.value = null
                }
            } catch (e: Exception) {

                _selectedMovie.value = null
            }
        }
    }
}
