package com.example.testtaskeffectivemobile.api

import com.example.testtaskeffectivemobile.data.model.Items
import com.example.testtaskeffectivemobile.data.model.Product
import okhttp3.FormBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService  {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010/")
    suspend fun getProducts(): Response<Items>
}