package com.example.lab04_frontend.Logica

import java.sql.Date

class Estudiante : Usuario {
    var nombre: String? = null
    var telefono = 0
    var email: String? = null
    var fechaNacimiento = ""
    var carrera: Carrera? = null

    constructor(cedula:Int, clave:String, rol:String, nombre:String, telefono:Int, email:String, fechaNacimiento:String, carrera:Carrera)
    : super(cedula, clave, rol){
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
        this.fechaNacimiento = fechaNacimiento
        this.carrera = carrera
    }

    constructor() : super(){
        this.nombre = ""
        this.telefono = 0
        this.email = ""
        this.fechaNacimiento = ""
        this.carrera = Carrera()
    }
}