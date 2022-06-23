package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Matricula

class MatriculaAdapter(var matriculas: ArrayList<Matricula>, var context: Context) :
    RecyclerView.Adapter<MatriculaAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatriculaAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_matriculas, null)
        return MatriculaAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatriculaAdapter.ViewHolder, position: Int) {
        holder.bindData(matriculas.get(position))
    }

    override fun getItemCount(): Int {
        return this.matriculas.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreCurso: TextView
        private var numeroGrupo: TextView

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.numeroGrupo = view.findViewById(R.id.numeroGrupo)
        }

        fun bindData(matricula: Matricula) {
            this.nombreCurso.setText(matricula.grupo!!.curso!!.nombre.toString())
            this.numeroGrupo.setText(matricula.grupo!!.codigo.toString())
        }
    }
}