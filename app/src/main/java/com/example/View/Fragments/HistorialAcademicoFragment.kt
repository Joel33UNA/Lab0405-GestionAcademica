package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CarreraApi
import com.example.Data.MatriculaApi
import com.example.View.Adapter.CarreraAdapter
import com.example.View.Adapter.HistorialAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class HistorialAcademicoFragment : Fragment() {
    private var allHistorial = ArrayList<Matricula>()
    private var listaHistorial = ArrayList<Matricula>()
    private lateinit var historialLiveData: MutableLiveData<List<Matricula>>
    private lateinit var historialAdapter: HistorialAdapter
    private lateinit var usuario: Usuario

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_historial_academico, container, false)

        //super.onCreate(savedInstanceState)
        this.usuario = activity!!.intent.extras!!.getSerializable("Usuario") as Usuario
        this.historialLiveData = MutableLiveData<List<Matricula>>()
        this.historialAdapter = HistorialAdapter(listaHistorial, context!!)

        initHistorial()
        observer()

        val searchView = view!!.findViewById<SearchView>(R.id.searchHistorial)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxHistorial = ArrayList<Matricula>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxHistorial.clear()
                    allHistorial.forEach{
                        if(it.grupo!!.curso!!.nombre.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            auxHistorial.add(it)
                        }
                    }
                    historialLiveData!!.value = auxHistorial
                }
                else{
                    auxHistorial.clear()
                    auxHistorial.addAll(allHistorial)
                    historialLiveData.value = auxHistorial
                }
                return false
            }
        })

        return view
    }

    private fun initHistorial() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(MatriculaApi::class.java)
            val request = call.get(usuario.cedula)
            val response = request?.body() as ArrayList<Matricula>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    allHistorial = response
                    historialLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar el historial académico", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        val historial: Observer<List<Matricula>> = object : Observer<List<Matricula>> {
            @Override
            override fun onChanged(@Nullable historial: List<Matricula>?) {
                listaHistorial = historial as ArrayList<Matricula>
                updateView()
            }
        }
        historialLiveData!!.observe(this, historial)
    }

    private fun updateView() {
        this.historialAdapter = HistorialAdapter(listaHistorial, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerHistorialAcademico)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = historialAdapter
    }
}