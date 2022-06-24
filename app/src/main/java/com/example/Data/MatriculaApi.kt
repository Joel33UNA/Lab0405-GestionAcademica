package com.example.Data

import com.example.lab04_frontend.Logica.Matricula
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MatriculaApi {
    @GET("matriculas")
    suspend fun getMatriculaAll(): Response<ArrayList<Matricula>>

    @GET("matriculas/{cedula}")
    suspend fun get(@Path("cedula")cedula: Int): Response<ArrayList<Matricula>>?

    @POST("matriculas")
    suspend fun add(@Body matricula: Matricula): Response<Void>
}