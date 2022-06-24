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
import com.example.Data.MatriculaApi
import com.example.View.Adapter.MatriculaAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.IPAddress
import com.example.lab04_frontend.Logica.Matricula
import com.example.lab04_frontend.Logica.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MatriculasFragment : Fragment() {

    private var allMatriculas = ArrayList<Matricula>()
    private lateinit var usuario: Usuario
    private var listaMatriculas = ArrayList<Matricula>()
    private lateinit var matriculasLiveData: MutableLiveData<List<Matricula>>
    private lateinit var matriculaAdapter: MatriculaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_matriculas, container, false)

        //super.onCreate(savedInstanceState)

        this.usuario = activity!!.intent.extras!!.getSerializable("Usuario") as Usuario
        this.matriculasLiveData = MutableLiveData<List<Matricula>>()
        this.matriculaAdapter = MatriculaAdapter(listaMatriculas, context!!)

        initMatriculas()
        observer()

        val searchView = view!!.findViewById<SearchView>(R.id.searchMatriculas)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxMatriculas = ArrayList<Matricula>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxMatriculas.clear()
                    allMatriculas.forEach{
                        if(it.grupo!!.curso!!.nombre.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            auxMatriculas.add(it)
                        }
                    }
                    matriculasLiveData!!.value = auxMatriculas
                }
                else{
                    auxMatriculas.clear()
                    auxMatriculas.addAll(allMatriculas)
                    matriculasLiveData.value = auxMatriculas
                }
                return false
            }
        })

        return view

    }

    private fun initMatriculas() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(MatriculaApi::class.java)
            val request = call.get(usuario.cedula)
            val response = request?.body() as ArrayList<Matricula>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    allMatriculas = response
                    matriculasLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar las matriculas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        val matricula: Observer<List<Matricula>> = object : Observer<List<Matricula>> {
            @Override
            override fun onChanged(@Nullable matriculas: List<Matricula>?) {
                listaMatriculas = matriculas as ArrayList<Matricula>
                updateView()
            }
        }
        matriculasLiveData!!.observe(this, matricula)
    }

    private fun updateView() {
        this.matriculaAdapter = MatriculaAdapter(listaMatriculas, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerMatriculas)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = matriculaAdapter
    }
}