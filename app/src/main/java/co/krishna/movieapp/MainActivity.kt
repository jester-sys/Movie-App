package co.krishna.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.krishna.movieapp.model.Movie
import co.krishna.movieapp.screen.MovieDetailScreen
import co.krishna.movieapp.screen.MovieListScreen
import co.krishna.movieapp.ui.theme.MovieAppTheme
import co.krishna.movieapp.viewModel.MovieViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(onMovieClick = { movie ->
                navController.navigate("movieDetail/${movie.id}")
            })
        }

        composable("movieDetail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()

            if (movieId != null) {

                val viewModel: MovieViewModel = viewModel()
                viewModel.fetchMovieDetails(movieId)
                val movie by viewModel.selectedMovie.collectAsState()

                if (movie != null) {
                    MovieDetailScreen(movie = movie!!, navController = navController)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading movie details...", color = Color.Gray)
                    }
                }
            } else {
                Text("Invalid movie ID", modifier = Modifier.fillMaxSize(), color = Color.Red)
            }
        }
    }
}
