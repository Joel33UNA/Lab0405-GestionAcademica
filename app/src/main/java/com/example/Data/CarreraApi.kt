package com.example.Data

import com.example.lab04_frontend.Logica.Carrera
import retrofit2.Response
import retrofit2.http.GET

interface CarreraApi {
    @GET("carreras")
    suspend fun getCarrerasAll(): Response<ArrayList<Carrera>>
}