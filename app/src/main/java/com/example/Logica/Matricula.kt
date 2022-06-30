package com.example.lab04_frontend.Logica

class Matricula : java.io.Serializable{
    var codigo = 0
    var nota = 0
    var estudiante: Estudiante? = null
    var grupo: Grupo? = null

    constructor(codigo: Int, estudiante: Estudiante, grupo: Grupo, nota: Int) {
        this.codigo = codigo
        this.nota = nota
        this.estudiante = estudiante
        this.grupo = grupo
    }

    constructor() {
        codigo = 0
        nota = 0
        estudiante = Estudiante()
        grupo = Grupo()
    }
}