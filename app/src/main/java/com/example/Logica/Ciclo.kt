package com.example.lab04_frontend.Logica

import java.sql.Date
import java.io.Serializable

class Ciclo : Serializable {
    var codigo = 0
    var anio = 0
    var numeroCiclo = 0
    var fechaInicio = ""
    var fechaFin = ""

    constructor(codigo: Int, anio: Int, numeroCiclo: Int, fechaInicio: String, fechaFin: String) {
        this.codigo = codigo
        this.anio = anio
        this.numeroCiclo = numeroCiclo
        this.fechaInicio = fechaInicio
        this.fechaFin = fechaFin
    }

    constructor() {
        codigo = 0
        anio = 0
        numeroCiclo = 0
        fechaInicio = ""
        fechaFin = ""
    }

    override fun toString(): String {
        return "$anio-$numeroCiclo"
    }
}