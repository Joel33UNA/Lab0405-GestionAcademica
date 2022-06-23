package com.example.Data

import com.example.lab04_frontend.Logica.Profesor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfesorApi {
    @GET("profesores")
    suspend fun getProfesoresAll(): Response<ArrayList<Profesor>>

    @GET("profesores/{cedula}")
    suspend fun get(@Path("cedula") cedula: Int): Response<Profesor>
}