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
import com.example.lab04_frontend.Logica.Grupo
import com.example.lab04_frontend.Logica.Matricula

class GruposMatriculaAdapter(var grupos: ArrayList<Grupo>, var context: Context) :
    RecyclerView.Adapter<GruposMatriculaAdapter.ViewHolder>(){

    var gruposLiveData: MutableLiveData<Grupo>
    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
        this.gruposLiveData = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_grupos_matricula, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GruposMatriculaAdapter.ViewHolder, position: Int) {
        holder.bindData(grupos.get(position))
    }

    override fun getItemCount(): Int {
        return this.grupos.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var nombreCurso: TextView
        var ciclo: TextView
        var nombreProfesor: TextView
        var horario: TextView
        var btnMatricular: Button

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.ciclo = view.findViewById(R.id.ciclo)
            this.nombreProfesor = view.findViewById(R.id.nombreProfesor)
            this.horario = view.findViewById(R.id.horario)
            this.btnMatricular = view.findViewById(R.id.matricularBtn)
        }

        fun bindData(grupo: Grupo) {
            this.nombreCurso.text = grupo.curso!!.nombre.toString()
            this.ciclo.text = grupo.ciclo.toString()
            this.nombreProfesor.text = grupo.profesor!!.nombre!!.toString()
            this.horario.text = grupo.horario.toString()
            this.btnMatricular.setOnClickListener { gruposLiveData.value = grupo }
        }
    }
}