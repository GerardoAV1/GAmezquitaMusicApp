package com.example1.gamezquitamusicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example1.gamezquitamusicapp.components.AlbumCard
import com.example1.gamezquitamusicapp.components.MiniPlayerBar
import com.example1.gamezquitamusicapp.components.RecentlyPlayedCard
import com.example1.gamezquitamusicapp.models.Albums
import com.google.gson.Gson

@Composable
fun HomeScreen(navController: NavHostController, albums: List<Albums>, loading: Boolean) {
    Scaffold(
        bottomBar = {
            if (albums.isNotEmpty()) {
                MiniPlayerBar(albums = albums.first())
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1E4FF))
                .padding(
                    start = 25.dp,
                    end = 25.dp,
                    top = 42.dp,
                    bottom = innerPadding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.verticalGradient(
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
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(albums) { album ->
                            val albumJson = Gson().toJson(album)
                            AlbumCard(
                                albums = album,
                                navController = navController
                            )
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
}


