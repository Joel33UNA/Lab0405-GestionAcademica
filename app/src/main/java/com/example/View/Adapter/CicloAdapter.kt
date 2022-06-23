package com.example.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.View.R
import com.example.lab04_frontend.Logica.Ciclo


class CicloAdapter(var ciclos: ArrayList<Ciclo>, var context: Context) :
    RecyclerView.Adapter<CicloAdapter.ViewHolder>() {

    var inflater : LayoutInflater

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CicloAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.list_ciclos, null)
        return CicloAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CicloAdapter.ViewHolder, position: Int) {
        holder.bindData(ciclos.get(position))
    }

    override fun getItemCount(): Int {
        return ciclos.size
    }

    fun setItems(items: ArrayList<Ciclo>){
        this.ciclos = items
    }

    class ViewHolder(var view:View) : RecyclerView.ViewHolder(view) {

        var iconImage: ImageView
        private var anio: TextView
        private var numeroCiclo: TextView
        private var fechaInicio: TextView
        private var fechaFin: TextView

        init{
            this.iconImage = view.findViewById(R.id.imageCiclo)
            this.anio = view.findViewById(R.id.anioCiclo)
            this.numeroCiclo = view.findViewById(R.id.numeroCiclo)
            this.fechaInicio = view.findViewById(R.id.fechaInicio)
            this.fechaFin = view.findViewById(R.id.fechaFinal)
        }

        fun bindData(ciclo: Ciclo){
            this.anio.setText(ciclo.anio.toString())
            this.numeroCiclo.setText(ciclo.numeroCiclo.toString())
            this.fechaInicio.setText(ciclo.fechaInicio.toString())
            this.fechaFin.setText(ciclo.fechaFin.toString())
        }
    }
}