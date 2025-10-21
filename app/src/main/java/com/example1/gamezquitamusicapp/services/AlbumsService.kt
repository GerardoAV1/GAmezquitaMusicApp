package com.example1.gamezquitamusicapp.services

import com.example1.gamezquitamusicapp.models.Albums
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumsService {
    @GET("albums")
    suspend fun getAllAlbums(): List<Albums>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Albums
}