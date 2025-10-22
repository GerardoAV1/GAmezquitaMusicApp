package com.example1.gamezquitamusicapp

import android.R.attr.type
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example1.gamezquitamusicapp.components.MiniPlayerBar
import com.example1.gamezquitamusicapp.models.Albums
import com.example1.gamezquitamusicapp.services.AlbumsService
import com.example1.gamezquitamusicapp.ui.theme.GAmezquitaMusicAppTheme
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GAmezquitaMusicAppTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val BASE_URL = "https://music.juanfrausto.com/api/"
    var albums by remember { mutableStateOf(listOf<Albums>()) }
    var loading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumsService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            albums = result
            loading = false
        } catch (e: Exception) {
            Log.e("MainApp", "Error cargando álbumes: ${e.message}")
            loading = false
        }
    }


    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController, albums = albums, loading = loading)
        }

        composable(
            route = "albumDetail/{album}",
            arguments = listOf(navArgument("album") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumJson = backStackEntry.arguments?.getString("album")
            val decodedAlbumJson = URLDecoder.decode(albumJson, StandardCharsets.UTF_8.toString())
            val album = Gson().fromJson(decodedAlbumJson, Albums::class.java)
            AlbumDetailScreen(album = album, navController = navController)
        }
    }
}

