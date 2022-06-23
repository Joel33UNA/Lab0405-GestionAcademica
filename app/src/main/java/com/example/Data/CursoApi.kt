package com.example.Data

import com.example.lab04_frontend.Logica.Curso
import retrofit2.Response
import retrofit2.http.GET

interface CursoApi {
    @GET("cursos")
    suspend fun getCursosAll(): Response<ArrayList<Curso>>
}