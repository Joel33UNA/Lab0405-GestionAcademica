package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.View.Adapter.EstudianteAdapter
import com.example.View.Adapter.EstudiantesGrupoAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.*
import java.util.*
import kotlin.collections.ArrayList

class EstudiantesGrupoFragment : Fragment() {
    private lateinit var matricula: Matricula
    private var listaEstudiantes = ArrayList<Estudiante>()
    private lateinit var estudiantesLiveData: MutableLiveData<List<Estudiante>>
    private lateinit var estudiantesAdapter: EstudiantesGrupoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_estudiantes_grupo, container, false)

        this.matricula = this.arguments!!.get("Matricula") as Matricula
        this.estudiantesLiveData = MutableLiveData<List<Estudiante>>()
        this.estudiantesAdapter = EstudiantesGrupoAdapter(listaEstudiantes, context!!)

        initEstudiantes()
        observer()

        return view
    }

    private fun initEstudiantes(){
        val estudiantes = ArrayList<Estudiante>()
        estudiantes.add(matricula.estudiante!!)
        estudiantesLiveData.value = estudiantes
    }

    private fun observer(){
        val observer: Observer<List<Estudiante>> = object : Observer<List<Estudiante>> {
            @Override
            override fun onChanged(@Nullable estudiantes: List<Estudiante>?) {
                listaEstudiantes = estudiantes as ArrayList<Estudiante>
                updateView()
            }
        }
        estudiantesLiveData!!.observe(this, observer)
    }

    fun updateView(){
        this.estudiantesAdapter = EstudiantesGrupoAdapter(listaEstudiantes, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerViewEstudiantesGrupo)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = estudiantesAdapter
        observer()
    }
}