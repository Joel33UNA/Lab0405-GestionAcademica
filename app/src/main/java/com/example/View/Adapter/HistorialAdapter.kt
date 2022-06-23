package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Matricula

class HistorialAdapter(var matriculas: ArrayList<Matricula>, var context: Context) :
    RecyclerView.Adapter<HistorialAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_historial, null)
        return HistorialAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialAdapter.ViewHolder, position: Int) {
        holder.bindData(matriculas.get(position))
    }

    override fun getItemCount(): Int {
        return this.matriculas.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreCurso: TextView
        private var numeroGrupo: TextView
        private var nota: TextView
        private var creditosCurso: TextView
        private var ciclo: TextView

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.numeroGrupo = view.findViewById(R.id.numeroGrupo)
            this.nota = view.findViewById(R.id.nota)
            this.creditosCurso = view.findViewById(R.id.creditosCurso)
            this.ciclo = view.findViewById(R.id.ciclo)
        }

        fun bindData(matricula: Matricula) {
            this.nombreCurso.setText(matricula.grupo!!.curso!!.nombre.toString())
            this.numeroGrupo.setText(matricula.grupo!!.codigo.toString())
            this.nota.setText(matricula.nota.toString())
            this.creditosCurso.setText(matricula.grupo!!.curso!!.creditos.toString())
            this.ciclo.setText(matricula.grupo!!.ciclo.toString())
        }
    }
}