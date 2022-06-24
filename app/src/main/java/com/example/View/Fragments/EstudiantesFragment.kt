package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CicloApi
import com.example.Data.EstudianteApi
import com.example.View.Adapter.EstudianteAdapter
import com.example.View.R
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

class EstudiantesFragment : Fragment() {

    private var allEstudiantes = ArrayList<Estudiante>()
    private var listaCiclos = ArrayList<Ciclo>()
    private var listaEstudiantes = ArrayList<Estudiante>()
    private lateinit var ciclosLiveData: MutableLiveData<List<Ciclo>>
    private lateinit var estudiantesLiveData: MutableLiveData<List<Estudiante>>
    private lateinit var estudianteAdapter: EstudianteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_estudiantes, container, false)

        this.ciclosLiveData = MutableLiveData<List<Ciclo>>()
        this.estudiantesLiveData = MutableLiveData<List<Estudiante>>()
        this.estudianteAdapter = EstudianteAdapter(listaEstudiantes, context!!)

        initCiclos()
        observerCiclos()
        initEstudiantes()
        observerEstudiantes()
        observerEstudianteUnico()

        val searchView = view!!.findViewById<SearchView>(R.id.searchViewEstudiantes)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxEstudiantes = ArrayList<Estudiante>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxEstudiantes.clear()
                    allEstudiantes.forEach{
                        if(it.nombre.toString().lowercase(Locale.getDefault()).contains(searchText)){
                            auxEstudiantes.add(it)
                        }
                    }
                    estudiantesLiveData!!.value = auxEstudiantes
                }
                else{
                    auxEstudiantes.clear()
                    auxEstudiantes.addAll(allEstudiantes)
                    estudiantesLiveData.value = auxEstudiantes
                }
                return false
            }
        })

        return view
    }

    private fun initCiclos(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CicloApi::class.java)
            val request = call.getCiclosAll()
            val response = request.body() as ArrayList<Ciclo>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    ciclosLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los estudiantes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerCiclos(){
        val ciclo: Observer<List<Ciclo>> = object : Observer<List<Ciclo>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Ciclo>?) {
                listaCiclos = ciclos as ArrayList<Ciclo>
                updateViewCiclos()
            }
        }
        ciclosLiveData!!.observe(this, ciclo)
    }

    private fun initEstudiantes() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(EstudianteApi::class.java)
            val request = call.getEstudiantesAll()
            val response = request.body() as ArrayList<Estudiante>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    allEstudiantes = response
                    estudiantesLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los estudiantes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerEstudiantes() {
        val estudiante: Observer<List<Estudiante>> = object : Observer<List<Estudiante>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Estudiante>?) {
                listaEstudiantes = ciclos as ArrayList<Estudiante>
                updateViewEstudiantes()
            }
        }
        estudiantesLiveData!!.observe(this, estudiante)
    }

    private fun observerEstudianteUnico(){
        val estudiante: Observer<Estudiante> = object : Observer<Estudiante> {
            @Override
            override fun onChanged(@Nullable estudiante: Estudiante) {
                var estudiante = estudiante as Estudiante
                val matricular = GruposMatriculaFragment()
                val spinnerCiclo = view!!.findViewById<Spinner>(R.id.spinnerCiclos)
                val ciclo = spinnerCiclo.selectedItem as Ciclo
                var bundle =  Bundle();
                bundle.putSerializable("Estudiante", estudiante)
                bundle.putSerializable("Ciclo", ciclo)
                matricular.arguments=bundle
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, matricular).commit()
            }
        }
        estudianteAdapter.estudianteLiveData.observe(this, estudiante)
    }

    private fun updateViewCiclos(){
        val spinnerCiclo = view!!.findViewById<Spinner>(R.id.spinnerCiclos)
        val cicloAdapter = ArrayAdapter(context!!,  android.R.layout.simple_spinner_item, listaCiclos)

        cicloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCiclo.adapter = cicloAdapter
    }

    private fun updateViewEstudiantes() {
        this.estudianteAdapter = EstudianteAdapter(listaEstudiantes, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerEstudiantes)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = estudianteAdapter
        observerEstudianteUnico()
    }
}