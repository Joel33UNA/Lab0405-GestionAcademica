package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Grupo
import org.w3c.dom.Text

class GrupoAdapter(var grupos: ArrayList<Grupo>, var context: Context) :
    RecyclerView.Adapter<GrupoAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_grupos, null)
        return GrupoAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoAdapter.ViewHolder, position: Int) {
        holder.bindData(grupos.get(position))
    }

    override fun getItemCount(): Int {
        return this.grupos.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var nombreCurso: TextView
        var ciclo: TextView
        var nombreCarrera: TextView
        var nombreProfesor: TextView
        var horario: TextView

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.ciclo = view.findViewById(R.id.ciclo)
            this.nombreCarrera = view.findViewById(R.id.nombreCarrera)
            this.nombreProfesor = view.findViewById(R.id.nombreProfesor)
            this.horario = view.findViewById(R.id.horario)
        }

        fun bindData(grupo: Grupo) {
            this.nombreCurso.setText(grupo.curso!!.nombre.toString())
            this.ciclo.setText(grupo.ciclo.toString())
            this.nombreCarrera.setText(grupo.curso!!.carrera!!.nombre.toString())
            this.nombreProfesor.setText(grupo.profesor!!.nombre.toString())
            this.horario.setText(grupo.horario.toString())
        }
    }
}