package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Spinner
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
import com.example.lab04_frontend.Logica.Ciclo
import com.example.lab04_frontend.Logica.Estudiante
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class CarrerasFragment : Fragment() {
    private var allCarreras = ArrayList<Carrera>()
    private var listaCarreras = ArrayList<Carrera>()
    private lateinit var carrerasLiveData: MutableLiveData<List<Carrera>>
    private lateinit var carreraAdapter: CarreraAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carreras, container, false)

        //super.onCreate(savedInstanceState)

        this.carrerasLiveData = MutableLiveData<List<Carrera>>()
        this.carreraAdapter = CarreraAdapter(listaCarreras, context!!)
        initCarreras()
        observer()
        observerCarreraUnica()

        val searchView = view!!.findViewById<SearchView>(R.id.searchCarreras)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxCarreras = ArrayList<Carrera>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxCarreras.clear()
                    allCarreras.forEach{
                        if(it.nombre.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            auxCarreras.add(it)
                        }
                    }
                    carrerasLiveData!!.value = auxCarreras
                }
                else{
                    auxCarreras.clear()
                    auxCarreras.addAll(allCarreras)
                    carrerasLiveData.value = auxCarreras
                }
                return false
            }
        })
        return view
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
                    allCarreras = response
                    carrerasLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar las carreras", Toast.LENGTH_SHORT).show()
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

    private fun observerCarreraUnica(){
        val carrera: Observer<Carrera> = object : Observer<Carrera> {
            @Override
            override fun onChanged(@Nullable carrera: Carrera?) {
                val matricular = AgregarCursoFragment()
                var bundle =  Bundle();
                bundle.putSerializable("Carrera", carrera)
                matricular.arguments=bundle
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, matricular).commit()
            }
        }
        carreraAdapter.carreraLiveData!!.observe(this, carrera)
    }

    private fun updateView() {
        this.carreraAdapter = CarreraAdapter(listaCarreras, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerCarreras)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = carreraAdapter
        observerCarreraUnica()
    }
}