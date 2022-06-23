package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CarreraApi
import com.example.View.Adapter.CarreraAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarrerasFragment : Fragment() {
    private var listaCarreras = ArrayList<Carrera>()
    private lateinit var carrerasLiveData: MutableLiveData<List<Carrera>>
    private lateinit var carreraAdapter: CarreraAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carreras, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.carrerasLiveData = MutableLiveData<List<Carrera>>()
        this.carreraAdapter = CarreraAdapter(listaCarreras, context!!)

        initCarreras()
        observer()
    }

    private fun initCarreras() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CarreraApi::class.java)
            val request = call.getCarrerasAll()
            val response = request.body() as ArrayList<Carrera>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    carrerasLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los ciclos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        val ciclo: Observer<List<Carrera>> = object : Observer<List<Carrera>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Carrera>?) {
                listaCarreras = ciclos as ArrayList<Carrera>
                updateView()
            }
        }
        carrerasLiveData!!.observe(this, ciclo)
    }

    private fun updateView() {
        this.carreraAdapter = CarreraAdapter(listaCarreras, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerCarreras)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = carreraAdapter
    }
}