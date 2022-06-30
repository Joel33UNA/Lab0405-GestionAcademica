package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Estudiante

class EstudiantesGrupoAdapter(var estudiantes: ArrayList<Estudiante>, var context: Context) :
    RecyclerView.Adapter<EstudiantesGrupoAdapter.ViewHolder>(){

    var estudianteLiveData: MutableLiveData<Estudiante>
    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
        this.estudianteLiveData = MutableLiveData()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_estudiantes_grupo, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudiantesGrupoAdapter.ViewHolder, position: Int) {
        holder.bindData(estudiantes.get(position))
    }

    override fun getItemCount(): Int {
        return estudiantes.size
    }

    fun getEstudiante(): MutableLiveData<Estudiante> {
        return this.estudianteLiveData!!
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreEstudiante: TextView = view.findViewById(R.id.nombreEstudiante)
        private var cedulaEstudiante: TextView = view.findViewById(R.id.cedulaEstudiante)
        private var nombreCarrera: TextView = view.findViewById(R.id.nombreCarrera)
        private var fechaNacimiento: TextView = view.findViewById(R.id.fechaNacimiento)
        private var telefonoEstudiante: TextView = view.findViewById(R.id.telefonoEstudiante)
        private var correoEstudiante: TextView = view.findViewById(R.id.correoEstudiante)
        var agregarNotaBtn: Button = view.findViewById(R.id.agregarNotaBtn)

        fun bindData(estudiante: Estudiante){
            this.nombreEstudiante.setText(estudiante.nombre.toString())
            this.cedulaEstudiante.setText(estudiante.cedula.toString())
            this.nombreCarrera.setText(estudiante.carrera!!.nombre.toString())
            this.fechaNacimiento.setText(estudiante.fechaNacimiento)
            this.telefonoEstudiante.setText(estudiante.telefono.toString())
            this.correoEstudiante.setText(estudiante.email.toString())
            this.agregarNotaBtn.setOnClickListener {  estudianteLiveData.value = estudiante }
        }
    }
}