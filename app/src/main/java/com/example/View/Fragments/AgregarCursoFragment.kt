package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import com.example.Data.CursoApi
import com.example.Data.GrupoApi
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.Curso
import com.example.lab04_frontend.Logica.Grupo
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.android.synthetic.main.fragment_agregar_curso.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AgregarCursoFragment : Fragment() {
    private lateinit var carrera: Carrera
    private val creditos = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_curso, container, false)

        this.carrera = this.arguments!!.get("Carrera") as Carrera
        this.creditos.addAll(listOf(1, 2, 3, 4, 5))

        val spinnerCreditos = view!!.findViewById<Spinner>(R.id.spinnerCreditos)
        val cicloAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, creditos)
        cicloAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCreditos.adapter = cicloAdapter
        view!!.findViewById<Button>(R.id.button).setOnClickListener { agregarCurso() }

        return view
    }

    private fun agregarCurso(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(CursoApi::class.java)
                val request = call.add(Curso(
                    0,
                    view!!.findViewById<EditText>(R.id.editTextNombreCurso).text.toString(),
                    view!!.findViewById<Spinner>(R.id.spinnerCreditos).selectedItem as Int,
                    Integer.parseInt(view!!.findViewById<EditText>(R.id.editTextHoras).text.toString()),
                    carrera
                ))
                if(request.isSuccessful){
                    withContext(Dispatchers.Main) {
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                            CarrerasFragment()).commit()
                    }
                }
            }
        }
        catch(exception:Exception){
            Toast.makeText(context!!, "Error agregando curso", Toast.LENGTH_SHORT).show()
        }
    }
}