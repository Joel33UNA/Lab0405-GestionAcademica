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
import com.example.Data.ProfesorApi
import com.example.View.Adapter.ProfesorAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.IPAddress
import com.example.lab04_frontend.Logica.Profesor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfesoresFragment : Fragment() {

    private var listaProfesores = ArrayList<Profesor>()
    private lateinit var profesoresLiveData: MutableLiveData<List<Profesor>>
    private lateinit var profesorAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.profesoresLiveData = MutableLiveData<List<Profesor>>()
        this.profesorAdapter = ProfesorAdapter(listaProfesores, context!!)

        initProfesores()
        observer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profesores, container, false)
    }

    private fun initProfesores() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(ProfesorApi::class.java)
            val request = call.getProfesoresAll()
            val response = request.body() as ArrayList<Profesor>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    profesoresLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los profesores", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        val ciclo: Observer<List<Profesor>> = object : Observer<List<Profesor>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Profesor>?) {
                listaProfesores = ciclos as ArrayList<Profesor>
                updateView()
            }
        }
        profesoresLiveData!!.observe(this, ciclo)
    }

    private fun updateView() {
        this.profesorAdapter = ProfesorAdapter(listaProfesores, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerProfesores)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = profesorAdapter
    }
}