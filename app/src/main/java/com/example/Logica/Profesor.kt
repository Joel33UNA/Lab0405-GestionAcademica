package com.example.lab04_frontend.Logica

class Profesor : Usuario{
    var nombre: String? = null
    var telefono = 0
    var email: String? = null

    constructor(nombre: String, telefono: Int, email: String, cedula: Int, clave: String, rol: String)
    : super(cedula, clave, rol){
        this.nombre = nombre
        this.telefono = telefono
        this.email = email
    }

    constructor() : super() {
        nombre = ""
        telefono = 0
        email = ""
    }

    override fun toString(): String {
        return "$nombre ${super.cedula}"
    }
}
