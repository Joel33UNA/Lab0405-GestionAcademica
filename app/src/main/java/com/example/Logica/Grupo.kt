package com.example.lab04_frontend.Logica

import java.io.Serializable

class Grupo : Serializable{
    var codigo = 0
    var horario: String? = null
    var curso: Curso? = null
    var ciclo: Ciclo? = null
    var profesor: Profesor? = null
    var estudiantes: ArrayList<Estudiante>? = null

    constructor(
        codigo: Int,
        horario: String,
        curso: Curso,
        ciclo: Ciclo,
        profesor: Profesor,
        estudiantes: ArrayList<Estudiante>
    ) {
        this.codigo = codigo
        this.horario = horario
        this.curso = curso
        this.ciclo = ciclo
        this.profesor = profesor
        this.estudiantes = estudiantes
    }

    constructor(){
        codigo = 0
        horario = ""
        curso = Curso()
        ciclo = Ciclo()
        profesor = Profesor()
        estudiantes = ArrayList()
    }
}