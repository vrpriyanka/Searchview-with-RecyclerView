package com.example.anmieapplication.data.network

class ApiHelper(private val apiService: ApiService) {
    suspend fun getListAnimes(searchParam: String) = apiService.getListAnimes(searchParam)
}