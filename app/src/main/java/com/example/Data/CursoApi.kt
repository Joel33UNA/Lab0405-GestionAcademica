package com.example.Data

import com.example.lab04_frontend.Logica.Curso
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CursoApi {
    @GET("cursos")
    suspend fun getCursosAll(): Response<ArrayList<Curso>>

    @POST("cursos")
    suspend fun add(@Body curso: Curso): Response<Void>
}