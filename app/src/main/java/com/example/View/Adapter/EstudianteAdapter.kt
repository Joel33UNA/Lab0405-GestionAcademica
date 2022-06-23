package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Curso
import com.example.lab04_frontend.Logica.Estudiante
import org.w3c.dom.Text

class EstudianteAdapter(var estudiantes: ArrayList<Estudiante>, var context: Context) :
    RecyclerView.Adapter<EstudianteAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_estudiantes, null)
        return EstudianteAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudianteAdapter.ViewHolder, position: Int) {
        holder.bindData(estudiantes.get(position))
    }

    override fun getItemCount(): Int {
        return estudiantes.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreEstudiante: TextView
        private var cedulaEstudiante: TextView
        private var nombreCarrera: TextView
        private var fechaNacimiento: TextView
        private var telefonoEstudiante: TextView
        private var correoEstudiante: TextView

        init{
            this.nombreEstudiante = view.findViewById(R.id.nombreEstudiante)
            this.cedulaEstudiante = view.findViewById(R.id.cedulaEstudiante)
            this.nombreCarrera = view.findViewById(R.id.nombreCarrera)
            this.fechaNacimiento = view.findViewById(R.id.fechaNacimiento)
            this.telefonoEstudiante = view.findViewById(R.id.telefonoEstudiante)
            this.correoEstudiante = view.findViewById(R.id.correoEstudiante)
        }

        fun bindData(estudiante: Estudiante){
            this.nombreEstudiante.setText(estudiante.nombre.toString())
            this.cedulaEstudiante.setText(estudiante.cedula.toString())
            this.nombreCarrera.setText(estudiante.carrera!!.nombre.toString())
            this.fechaNacimiento.setText(estudiante.fechaNacimiento)
            this.telefonoEstudiante.setText(estudiante.telefono.toString())
            this.correoEstudiante.setText(estudiante.email.toString())
        }
    }
}