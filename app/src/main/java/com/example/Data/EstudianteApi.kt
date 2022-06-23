package com.example.Data

import com.example.lab04_frontend.Logica.Estudiante
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EstudianteApi {
    @GET("estudiantes")
    suspend fun getEstudiantesAll(): Response<ArrayList<Estudiante>>

    @GET("estudiantes/{cedula}")
    suspend fun get(@Path("cedula") cedula: Int): Response<Estudiante>
}