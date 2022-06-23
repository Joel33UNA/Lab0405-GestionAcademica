package com.example.lab04_frontend.Logica

import java.io.Serializable

open class Usuario : Serializable {
    var cedula: Int = 0
    var clave: String? = null
    var rol: String? = null

    constructor(cedula:Int, clave:String, rol:String?) {
        this.cedula = cedula
        this.rol = rol
        this.clave = clave
    }

    constructor() {
        this.cedula = 0
        this.rol = ""
        this.clave = ""
    }

    constructor(cedula: Int) {
        this.cedula = cedula
        this.rol = ""
        this.clave = ""
    }
}