package com.example.lab04_frontend.Logica

class Carrera {
    var codigo: Int = 0
    var nombre: String? = null
    var titulo: String? = null
    var cursos: ArrayList<Curso>? = null

    constructor(codigo:Int, nombre:String, titulo:String, cursos:ArrayList<Curso>){
        this.codigo = codigo
        this.nombre = nombre
        this.titulo = titulo
        this.cursos = cursos
    }

    constructor(){
        this.codigo = 0
        this.nombre = ""
        this.titulo = ""
        this.cursos = ArrayList<Curso>()
    }

    override fun toString(): String {
        return this.nombre!!
    }
}
