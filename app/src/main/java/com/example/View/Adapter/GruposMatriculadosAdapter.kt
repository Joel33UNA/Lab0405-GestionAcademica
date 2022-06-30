package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Grupo
import com.example.lab04_frontend.Logica.Matricula

class GruposMatriculadosAdapter(var matriculas: ArrayList<Matricula>, var context: Context) :
    RecyclerView.Adapter<GruposMatriculadosAdapter.ViewHolder>(){

    var matriculasLiveData: MutableLiveData<Matricula>
    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
        this.matriculasLiveData = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_matriculados, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GruposMatriculadosAdapter.ViewHolder, position: Int) {
        holder.bindData(matriculas.get(position))
    }

    override fun getItemCount(): Int {
        return this.matriculas.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private val nombreCurso: TextView
        private val ciclo: TextView
        private val textViewNota: TextView
        private val textViewCreditos: TextView

        init {
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.ciclo = view.findViewById(R.id.ciclo)
            this.textViewNota = view.findViewById(R.id.textViewNota)
            this.textViewCreditos = view.findViewById(R.id.textViewCreditos)
        }

        fun bindData(matricula: Matricula) {
            this.nombreCurso.text = matricula.grupo!!.curso!!.nombre.toString()
            this.ciclo.text = matricula.grupo!!.ciclo.toString()
            this.textViewNota.text = matricula.nota.toString()
            this.textViewCreditos.text = matricula.grupo!!.curso!!.creditos.toString()
        }
    }
}