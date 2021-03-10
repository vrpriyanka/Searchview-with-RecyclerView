package com.example.anmieapplication.data.network

import com.example.anmieapplication.data.response.SearchResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/anime")
    suspend fun getListAnimes(@Query("q") searchParam: String): SearchResultModel

}