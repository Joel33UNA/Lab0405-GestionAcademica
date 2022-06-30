package com.example.Data

import com.example.lab04_frontend.Logica.Grupo
import retrofit2.Response
import retrofit2.http.*

interface GrupoApi {
    @GET("grupos/{codigoCarrera}/{codigoCiclo}")
    suspend fun getGruposCiclos(@Path("codigoCarrera")codCarrera: Int, @Path("codigoCiclo")codCiclo: Int): Response<ArrayList<Grupo>>

    @PUT("grupos")
    suspend fun update(@Body grupo: Grupo): Response<Void>

    @POST("grupos")
    suspend fun add(@Body grupo:Grupo): Response<Void>
}