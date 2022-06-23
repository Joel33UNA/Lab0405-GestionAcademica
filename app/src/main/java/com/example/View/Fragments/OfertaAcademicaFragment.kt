package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CarreraApi
import com.example.Data.CicloApi
import com.example.Data.GrupoApi
import com.example.View.Adapter.GrupoAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.Ciclo
import com.example.lab04_frontend.Logica.Grupo
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OfertaAcademicaFragment : Fragment() {

    private var listaGrupos = ArrayList<Grupo>()
    private var listaCarreras = ArrayList<Carrera>()
    private var listaCiclos = ArrayList<Ciclo>()
    private lateinit var gruposLiveData: MutableLiveData<List<Grupo>>
    private lateinit var carrerasLiveData: MutableLiveData<List<Carrera>>
    private lateinit var ciclosLiveData: MutableLiveData<List<Ciclo>>
    private lateinit var grupoAdapter: GrupoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_oferta_academica, container, false)

        this.gruposLiveData = MutableLiveData<List<Grupo>>()
        this.carrerasLiveData = MutableLiveData<List<Carrera>>()
        this.ciclosLiveData = MutableLiveData<List<Ciclo>>()
        this.grupoAdapter = GrupoAdapter(listaGrupos, context!!)

        initCarreras()
        observerCarreras()
        initCiclos()
        observerCiclos()

        val buscarOfertaAcademicaBtn = view!!.findViewById<Button>(R.id.buscarOfertaAcademicaBtn)
        buscarOfertaAcademicaBtn.setOnClickListener{
            initGrupos()
            observerGrupos()
        }

        return view
    }

    private fun initCarreras(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CarreraApi::class.java)
            val request = call.getCarrerasAll()
            val response = request?.body() as ArrayList<Carrera>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    carrerasLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar las carreras", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initCiclos(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CicloApi::class.java)
            val request = call.getCiclosAll()
            val response = request?.body() as ArrayList<Ciclo>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    ciclosLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los ciclos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initGrupos() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val spinnerCiclo = view!!.findViewById<Spinner>(R.id.spinnerCiclo)
            val spinnerCarrera = view!!.findViewById<Spinner>(R.id.spinnerCarrera)
            val ciclo = spinnerCiclo.selectedItem as Ciclo
            val carrera = spinnerCarrera.selectedItem as Carrera

            val call = retrofit.create(GrupoApi::class.java)
            val request = call.getGruposCiclos(carrera.codigo, ciclo.codigo)
            val response = request?.body() as ArrayList<Grupo>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    gruposLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar las matriculas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerCarreras() {
        val carrera: Observer<List<Carrera>> = object : Observer<List<Carrera>> {
            @Override
            override fun onChanged(@Nullable carreras: List<Carrera>?) {
                listaCarreras = carreras as ArrayList<Carrera>
                updateViewCarreras()
            }
        }
        carrerasLiveData!!.observe(this, carrera)
    }

    private fun observerCiclos() {
        val ciclo: Observer<List<Ciclo>> = object : Observer<List<Ciclo>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Ciclo>?) {
                listaCiclos = ciclos as ArrayList<Ciclo>
                updateViewCiclos()
            }
        }
        ciclosLiveData!!.observe(this, ciclo)
    }

    private fun observerGrupos() {
        val grupo: Observer<List<Grupo>> = object : Observer<List<Grupo>> {
            @Override
            override fun onChanged(@Nullable matriculas: List<Grupo>?) {
                listaGrupos = matriculas as ArrayList<Grupo>
                updateViewGrupos()
            }
        }
        gruposLiveData!!.observe(this, grupo)
    }

    private fun updateViewCarreras() {
        val spinnerCarrera = view!!.findViewById<Spinner>(R.id.spinnerCarrera)
        val carreraAdapter = ArrayAdapter<Carrera>(context!!,  android.R.layout.simple_spinner_item, listaCarreras)

        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarrera.adapter = carreraAdapter
    }

    private fun updateViewCiclos() {
        val spinnerCiclo = view!!.findViewById<Spinner>(R.id.spinnerCiclo)
        val cicloAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listaCiclos)

        cicloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCiclo.adapter = cicloAdapter
    }

    private fun updateViewGrupos() {
        this.grupoAdapter = GrupoAdapter(listaGrupos, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerGrupos)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = grupoAdapter
    }
}