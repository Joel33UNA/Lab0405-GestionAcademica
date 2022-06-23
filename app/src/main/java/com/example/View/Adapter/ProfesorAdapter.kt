package com.example.View.Adapter

import android.content.Context
import android.graphics.text.TextRunShaper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Profesor
import org.w3c.dom.Text

class ProfesorAdapter(var profesores: ArrayList<Profesor>, var context: Context) :
    RecyclerView.Adapter<ProfesorAdapter.ViewHolder>(){

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_profesores, null)
        return ProfesorAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfesorAdapter.ViewHolder, position: Int) {
        holder.bindData(profesores.get(position))
    }

    override fun getItemCount(): Int {
        return profesores.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var nombreProfesor: TextView
        private var cedulaProfesor: TextView
        private var telefonoProfesor: TextView
        private var correoProfesor: TextView

        init{
            this.nombreProfesor = view.findViewById(R.id.nombreProfesor)
            this.cedulaProfesor = view.findViewById(R.id.cedulaProfesor)
            this.telefonoProfesor = view.findViewById(R.id.telefonoProfesor)
            this.correoProfesor = view.findViewById(R.id.correoProfesor)
        }

        fun bindData(profesor: Profesor){
            this.nombreProfesor.setText(profesor.nombre.toString())
            this.cedulaProfesor.setText(profesor.cedula.toString())
            this.telefonoProfesor.setText(profesor.telefono.toString())
            this.correoProfesor.setText(profesor.email.toString())
        }
    }
}