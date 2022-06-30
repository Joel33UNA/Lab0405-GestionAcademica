package com.example.lab04_frontend.Logica

class Curso : java.io.Serializable{
    var codigo = 0
    var nombre: String? = null
    var creditos = 0
    var horasSemanales = 0
    var carrera: Carrera? = null

    constructor(codigo: Int, nombre: String, creditos: Int, horasSemanales: Int, carrera: Carrera) {
        this.codigo = codigo
        this.nombre = nombre
        this.creditos = creditos
        this.horasSemanales = horasSemanales
        this.carrera = carrera
    }

    constructor() {
        codigo = 0
        nombre = ""
        creditos = 0
        horasSemanales = 0
        carrera = Carrera()
    }
}
