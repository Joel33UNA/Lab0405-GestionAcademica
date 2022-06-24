package com.example.Data

import com.example.lab04_frontend.Logica.Grupo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GrupoApi {
    @GET("grupos/{codigoCarrera}/{codigoCiclo}")
    suspend fun getGruposCiclos(@Path("codigoCarrera")codCarrera: Int, @Path("codigoCiclo")codCiclo: Int): Response<ArrayList<Grupo>>

    @PUT("grupos")
    suspend fun update(@Body grupo: Grupo): Response<Void>
}