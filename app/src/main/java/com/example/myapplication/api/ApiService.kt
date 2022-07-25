package com.example.myapplication.api

import com.example.myapplication.data.Result
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by msaycon on 17,Jul,2022
 */

/**
 *  For API request using retrofit
 */

interface ApiService {
    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all")
    suspend fun getMedia(): Response<Result>
}