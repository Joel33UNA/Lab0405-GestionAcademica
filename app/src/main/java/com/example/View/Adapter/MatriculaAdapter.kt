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
import com.example.lab04_frontend.Logica.Matricula

class MatriculaAdapter(var matriculas: ArrayList<Matricula>, var context: Context) :
    RecyclerView.Adapter<MatriculaAdapter.ViewHolder>(){

    var matriculaLiveData: MutableLiveData<Matricula>
    var inflater : LayoutInflater

    init {
        this.matriculaLiveData = MutableLiveData()
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_matriculas, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatriculaAdapter.ViewHolder, position: Int) {
        holder.bindData(matriculas.get(position))
    }

    override fun getItemCount(): Int {
        return this.matriculas.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreCurso: TextView
        private var numeroGrupo: TextView
        private var verEstudiantesBtn: Button

        init{
            this.nombreCurso = view.findViewById(R.id.nombreCurso)
            this.numeroGrupo = view.findViewById(R.id.numeroGrupo)
            this.verEstudiantesBtn = view.findViewById(R.id.verEstudiantesBtn)
        }

        fun bindData(matricula: Matricula) {
            this.nombreCurso.setText(matricula.grupo!!.curso!!.nombre.toString())
            this.numeroGrupo.setText(matricula.grupo!!.codigo.toString())
            this.verEstudiantesBtn.setOnClickListener { matriculaLiveData.value = matricula }
        }
    }
}