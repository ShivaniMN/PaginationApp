package com.example.paginationapp.network

import com.example.paginationapp.models.DataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getAllData(): Call<List<DataItem>>

}