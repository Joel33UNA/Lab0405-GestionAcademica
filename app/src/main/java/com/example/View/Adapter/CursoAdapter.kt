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

class CursoAdapter(var cursos: ArrayList<Curso>, var context: Context) :
    RecyclerView.Adapter<CursoAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_cursos, null)
        return CursoAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoAdapter.ViewHolder, position: Int) {
        holder.bindData(cursos.get(position))
    }

    override fun getItemCount(): Int {
        return cursos.size
    }

    fun setItems(items: ArrayList<Curso>){
        this.cursos = items
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var iconImage: ImageView
        private var curso: TextView
        private var codigoCurso: TextView
        private var nombreCarrera: TextView
        private var creditosCurso: TextView
        private var horasSemanalesCurso: TextView

        init{
            this.iconImage = view.findViewById(R.id.imageCurso)
            this.curso = view.findViewById(R.id.nombreCurso)
            this.codigoCurso = view.findViewById(R.id.codigoCurso)
            this.nombreCarrera = view.findViewById(R.id.nombreCarrera)
            this.creditosCurso = view.findViewById(R.id.creditosCurso)
            this.horasSemanalesCurso = view.findViewById(R.id.horasSemanalesCurso)
        }

        fun bindData(curso: Curso){
            this.curso.setText(curso.nombre.toString())
            this.codigoCurso.setText(curso.codigo.toString())
            this.nombreCarrera.setText(curso.carrera?.nombre.toString())
            this.creditosCurso.setText(curso.creditos.toString())
            this.horasSemanalesCurso.setText(curso.horasSemanales.toString())
        }
    }
}