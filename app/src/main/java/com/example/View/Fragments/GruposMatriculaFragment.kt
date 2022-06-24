package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.GrupoApi
import com.example.Data.MatriculaApi
import com.example.View.Adapter.EstudianteAdapter
import com.example.View.Adapter.GruposMatriculaAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GruposMatriculaFragment : Fragment() {

    private var listaGrupos = ArrayList<Grupo>()
    private lateinit var estudiante: Estudiante
    private lateinit var ciclo: Ciclo
    private lateinit var gruposLiveData: MutableLiveData<List<Grupo>>
    private lateinit var gruposMatriculaAdapter: GruposMatriculaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matricular, container, false)

        this.estudiante = this.arguments!!.get("Estudiante") as Estudiante
        this.ciclo = this.arguments!!.get("Ciclo") as Ciclo

        view!!.findViewById<TextView>(R.id.textViewCedula).setText(estudiante.cedula.toString())
        view!!.findViewById<TextView>(R.id.textViewNombre).setText(estudiante.nombre.toString())
        view!!.findViewById<TextView>(R.id.textViewCarrera).setText(estudiante.carrera!!.nombre.toString())

        this.gruposLiveData = MutableLiveData()
        this.gruposMatriculaAdapter = GruposMatriculaAdapter(listaGrupos, context!!)

        initGrupos()
        observerGrupos()
        observerGrupoMatricula()

        return view
    }



    private fun initGrupos(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(GrupoApi::class.java)
                val request = call.getGruposCiclos(estudiante.carrera!!.codigo, ciclo.codigo)
                val response = request.body() as ArrayList<Grupo>
                if(request.isSuccessful){
                    withContext(Dispatchers.Main) {
                        gruposLiveData!!.value = response
                    }
                }
            }
        }
        catch(exception: java.lang.Exception){
            Toast.makeText(context!!, "Error al mostrar los grupos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observerGrupos(){
        val grupo: Observer<List<Grupo>> = object : Observer<List<Grupo>> {
            @Override
            override fun onChanged(@Nullable grupos: List<Grupo>?) {
                listaGrupos = grupos as ArrayList<Grupo>
                updateViewGrupos()
            }
        }
        gruposLiveData!!.observe(this, grupo)
    }

    private fun observerGrupoMatricula(){
        val observer: Observer<Grupo> = object : Observer<Grupo> {
            @Override
            override fun onChanged(@Nullable grupo: Grupo) {
                var dir = IPAddress()
                val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
                    .addConverterFactory(GsonConverterFactory.create()).build()

                try{
                    CoroutineScope(Dispatchers.IO).launch {
                        val call = retrofit.create(MatriculaApi::class.java)
                        val matricula = Matricula(0, estudiante, grupo, 0)
                        val request = call.add(matricula)
                        if(request.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                activity!!.supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, EstudiantesFragment()).commit()
                            }
                        }
                    }
                }
                catch(exception: Exception){
                    Toast.makeText(context!!, "Error al matricular!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        gruposMatriculaAdapter.gruposLiveData.observe(this, observer)
    }

    fun updateViewGrupos(){
        this.gruposMatriculaAdapter = GruposMatriculaAdapter(listaGrupos, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerGruposMatricula)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = gruposMatriculaAdapter
        observerGrupoMatricula()
    }
}