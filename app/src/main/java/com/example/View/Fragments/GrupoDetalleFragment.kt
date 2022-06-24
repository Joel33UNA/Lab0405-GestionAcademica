package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.Data.CicloApi
import com.example.Data.GrupoApi
import com.example.Data.ProfesorApi
import com.example.View.R
import com.example.lab04_frontend.Logica.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GrupoDetalleFragment : Fragment() {

    lateinit var grupo: Grupo
    private var listaCiclos = ArrayList<Ciclo>()
    private var listaProfesores = ArrayList<Profesor>()
    private lateinit var ciclosLiveData: MutableLiveData<List<Ciclo>>
    private lateinit var profesoresLiveData: MutableLiveData<List<Profesor>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grupo_detalle, container, false)

        this.grupo = this.arguments!!.get("Grupo") as Grupo

        val textViewCodigo = view!!.findViewById<TextView>(R.id.textViewCodigo)
        val textViewCurso = view!!.findViewById<TextView>(R.id.textViewCurso)
        val editTextHorario = view!!.findViewById<EditText>(R.id.editTextHorario)
        textViewCodigo.text = grupo.codigo.toString()
        textViewCurso.text = grupo.curso!!.nombre.toString()
        editTextHorario.setText(grupo.horario.toString())
        view!!.findViewById<Button>(R.id.editarGrupoBtn).setOnClickListener { actualizarGrupo() }

        this.ciclosLiveData = MutableLiveData()
        this.profesoresLiveData = MutableLiveData()

        initCiclos()
        observerCiclos()
        initProfesores()
        observerProfesores()

        return view
    }

    private fun actualizarGrupo() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(GrupoApi::class.java)
                val request = call.update(Grupo(
                    grupo.codigo,
                    view!!.findViewById<EditText>(R.id.editTextHorario).text.toString(),
                    grupo.curso!!,
                    view!!.findViewById<Spinner>(R.id.spinnerCiclos).selectedItem as Ciclo,
                    view!!.findViewById<Spinner>(R.id.spinnerProfesores).selectedItem as Profesor,
                    grupo.estudiantes!!
                ))
                if(request.isSuccessful){
                    withContext(Dispatchers.Main) {
                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, OfertaAcademicaFragment()).commit()
                    }
                }
            }
        }
        catch(exception:java.lang.Exception){
            Toast.makeText(context!!, "Error al mostrar los grupos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initCiclos() {
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
                Toast.makeText(context!!, "Error al mostrar los ciclos", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun initProfesores() {
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(ProfesorApi::class.java)
                val request = call.getProfesoresAll()
                val response = request.body() as ArrayList<Profesor>
                if(request.isSuccessful){
                    withContext(Dispatchers.Main) {
                        for (profesor in response) {
                            if(grupo.profesor!!.nombre.equals(profesor.nombre)){
                                listaProfesores.add(profesor)
                            }
                        }
                        profesoresLiveData!!.value = response
                    }
                }
            }
        }
        catch(exception: Exception){
            Toast.makeText(context!!, "Error al mostrar los estudiantes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observerProfesores() {
        val observer: Observer<List<Profesor>> = object : Observer<List<Profesor>> {
            @Override
            override fun onChanged(@Nullable profesores: List<Profesor>?) {
                listaProfesores = profesores as ArrayList<Profesor>
                updateViewProfesores()
            }
        }
        profesoresLiveData!!.observe(this, observer)
    }

    private fun updateViewCiclos(){
        val spinnerCiclo = view!!.findViewById<Spinner>(R.id.spinnerCiclos)
        val cicloAdapter = ArrayAdapter(context!!,  android.R.layout.simple_spinner_item, listaCiclos)

        cicloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCiclo.adapter = cicloAdapter
    }

    private fun updateViewProfesores(){
        val spinnerProfesor = view!!.findViewById<Spinner>(R.id.spinnerProfesores)
        val profesorAdapter = ArrayAdapter(context!!,  android.R.layout.simple_spinner_item, listaProfesores)

        profesorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProfesor.adapter = profesorAdapter
    }
}