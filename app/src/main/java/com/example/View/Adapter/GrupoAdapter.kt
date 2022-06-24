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
import org.w3c.dom.Text

class GrupoAdapter(var grupos: ArrayList<Grupo>, var context: Context) :
    RecyclerView.Adapter<GrupoAdapter.ViewHolder>(){

    var gruposLiveData: MutableLiveData<Grupo>
    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
        this.gruposLiveData = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_grupos, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoAdapter.ViewHolder, position: Int) {
        holder.bindData(grupos.get(position))
    }

    override fun getItemCount(): Int {
        return this.grupos.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var nombreCurso: TextView
        var ciclo: TextView
        var nombreCarrera: TextView
        var nombreProfesor: TextView
        var horario: TextView
        var detalleBtn: Button

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.ciclo = view.findViewById(R.id.ciclo)
            this.nombreCarrera = view.findViewById(R.id.nombreCarrera)
            this.nombreProfesor = view.findViewById(R.id.nombreProfesor)
            this.horario = view.findViewById(R.id.horario)
            this.detalleBtn = view.findViewById(R.id.detalleBtn)
        }

        fun bindData(grupo: Grupo) {
            this.nombreCurso.setText(grupo.curso!!.nombre.toString())
            this.ciclo.setText(grupo.ciclo.toString())
            this.nombreCarrera.setText(grupo.curso!!.carrera!!.nombre.toString())
            this.nombreProfesor.setText(grupo.profesor!!.nombre.toString())
            this.horario.setText(grupo.horario.toString())
            this.detalleBtn.setOnClickListener { gruposLiveData.value = grupo }
        }
    }
}