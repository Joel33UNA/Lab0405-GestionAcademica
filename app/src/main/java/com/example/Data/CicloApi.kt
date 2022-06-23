package com.example.Data

import com.example.lab04_frontend.Logica.Ciclo
import retrofit2.Response
import retrofit2.http.GET

interface CicloApi {
    @GET("ciclos")
    suspend fun getCiclosAll(): Response<ArrayList<Ciclo>>
}