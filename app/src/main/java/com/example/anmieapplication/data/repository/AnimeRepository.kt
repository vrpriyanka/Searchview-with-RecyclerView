package com.example.anmieapplication.data.repository

import com.example.anmieapplication.data.network.ApiHelper

class AnimeRepository(private val apiHelper: ApiHelper) {
    suspend fun getListAnimes(searchParam : String) = apiHelper.getListAnimes(searchParam)
}