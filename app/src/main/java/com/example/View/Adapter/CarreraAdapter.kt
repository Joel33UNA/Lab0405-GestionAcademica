package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.Ciclo

class CarreraAdapter(var carreras: ArrayList<Carrera>, var context: Context) :
    RecyclerView.Adapter<CarreraAdapter.ViewHolder>() {

    var carreraLiveData: MutableLiveData<Carrera>
    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
        this.carreraLiveData = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_carreras, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarreraAdapter.ViewHolder, position: Int) {
        holder.bindData(carreras.get(position))
    }

    override fun getItemCount(): Int {
        return carreras.size
    }

    fun setItems(items: ArrayList<Carrera>){
        this.carreras = items
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var iconImage: ImageView
        private var codigo: TextView
        private var nombreCarrera: TextView
        private var tituloCarrera: TextView
        private var agregarCursoBtn: Button

        init{
            this.iconImage = view.findViewById(R.id.imageCarrera)
            this.codigo = view.findViewById(R.id.idCarrera)
            this.nombreCarrera = view.findViewById(R.id.nombreCarrera)
            this.tituloCarrera = view.findViewById(R.id.tituloCarrera)
            this.agregarCursoBtn = view.findViewById(R.id.agregarCursoBtn)
        }

        fun bindData(carrera: Carrera){
            this.codigo.setText(carrera.codigo.toString())
            this.nombreCarrera.setText(carrera.nombre.toString())
            this.tituloCarrera.setText(carrera.titulo.toString())
            this.agregarCursoBtn.setOnClickListener { carreraLiveData.value = carrera }
        }
    }
}