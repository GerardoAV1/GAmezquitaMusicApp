package com.example1.gamezquitamusicapp

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example1.gamezquitamusicapp.components.AlbumCard
import com.example1.gamezquitamusicapp.components.RecentlyPlayedCard
import com.example1.gamezquitamusicapp.models.Albums
import com.example1.gamezquitamusicapp.services.AlbumsService
import com.example1.gamezquitamusicapp.ui.theme.GAmezquitaMusicAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GAmezquitaMusicAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    // Estados y Retrofit
    val BASE_URL = "https://music.juanfrausto.com/api/"
    var albums by remember { mutableStateOf(listOf<Albums>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            Log.i("MainApp", "Creando Instancia de Retrofit")
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumsService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            albums = result
            loading = false
        } catch (e: Exception) {
            Log.e("MainApp", "Algo falló: ${e.message}")
            loading = false
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1E4FF))
            .padding(horizontal = 25.dp, vertical = 42.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        item {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF9C6BFF),
                                Color(0xFF7B47F5)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Good Morning!",
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 9.dp)
                    )
                    Text(
                        text = "Gerardo Amezquita",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }


        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Albums",
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
                Text(
                    text = "See more",
                    color = Color(0xFF6B2EFF),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


        item {
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(albums) { album ->
                        AlbumCard(albums = album)
                    }
                }
            }
        }


        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recently Played",
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
                Text(
                    text = "See more",
                    color = Color(0xFF6B2EFF),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


        if (!loading) {
            items(albums.take(4)) { album ->
                RecentlyPlayedCard(albums = album)
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7)
@Composable
fun GreetingPreview() {
    GAmezquitaMusicAppTheme {
        MainApp()
    }
}