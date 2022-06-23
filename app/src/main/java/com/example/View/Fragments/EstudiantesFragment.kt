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
import com.example.Data.EstudianteApi
import com.example.View.Adapter.EstudianteAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Estudiante
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EstudiantesFragment : Fragment() {

    private var listaEstudiantes = ArrayList<Estudiante>()
    private lateinit var estudiantesLiveData: MutableLiveData<List<Estudiante>>
    private lateinit var estudianteAdapter: EstudianteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estudiantes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.estudiantesLiveData = MutableLiveData<List<Estudiante>>()
        this.estudianteAdapter = EstudianteAdapter(listaEstudiantes, context!!)

        initEstudiantes()
        observer()
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
                    estudiantesLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los estudiantes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        val ciclo: Observer<List<Estudiante>> = object : Observer<List<Estudiante>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Estudiante>?) {
                listaEstudiantes = ciclos as ArrayList<Estudiante>
                updateView()
            }
        }
        estudiantesLiveData!!.observe(this, ciclo)
    }

    private fun updateView() {
        this.estudianteAdapter = EstudianteAdapter(listaEstudiantes, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerEstudiantes)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = estudianteAdapter
    }
}