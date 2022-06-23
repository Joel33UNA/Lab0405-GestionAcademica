package com.example.lab0405_frontend.Data

import com.example.lab04_frontend.Logica.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("sesiones/comprobar")
    suspend fun comprobar(@Body usuario: Usuario): Response<Usuario>
}