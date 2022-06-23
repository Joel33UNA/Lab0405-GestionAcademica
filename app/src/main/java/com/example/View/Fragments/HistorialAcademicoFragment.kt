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
import com.example.Data.MatriculaApi
import com.example.View.Adapter.CarreraAdapter
import com.example.View.Adapter.HistorialAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.IPAddress
import com.example.lab04_frontend.Logica.Matricula
import com.example.lab04_frontend.Logica.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialAcademicoFragment : Fragment() {
    private var listaHistorial = ArrayList<Matricula>()
    private lateinit var historialLiveData: MutableLiveData<List<Matricula>>
    private lateinit var historialAdapter: HistorialAdapter
    private lateinit var usuario: Usuario

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial_academico, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.usuario = activity!!.intent.extras!!.getSerializable("Usuario") as Usuario
        this.historialLiveData = MutableLiveData<List<Matricula>>()
        this.historialAdapter = HistorialAdapter(listaHistorial, context!!)

        initHistorial()
        observer()
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