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
import com.example.Data.CursoApi
import com.example.View.Adapter.CursoAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Carrera
import com.example.lab04_frontend.Logica.Curso
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class CursosFragment : Fragment() {

    private var allCursos = ArrayList<Curso>()
    private var listaCursos = ArrayList<Curso>()
    private lateinit var cursosLiveData: MutableLiveData<List<Curso>>
    private lateinit var cursoAdapter: CursoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cursos, container, false)

        //super.onCreate(savedInstanceState)

        this.cursosLiveData = MutableLiveData<List<Curso>>()
        this.cursoAdapter = CursoAdapter(listaCursos, context!!)

        initCursos()
        observer()

        val searchView = view!!.findViewById<SearchView>(R.id.searchCursos)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxCursos = ArrayList<Curso>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxCursos.clear()
                    allCursos.forEach{
                        if(it.nombre.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            auxCursos.add(it)
                        }
                    }
                    cursosLiveData!!.value = auxCursos
                }
                else{
                    auxCursos.clear()
                    auxCursos.addAll(allCursos)
                    cursosLiveData.value = auxCursos
                }
                return false
            }
        })

        return view
    }

    private fun observer() {
        val curso: Observer<List<Curso>> = object : Observer<List<Curso>> {
            @Override
            override fun onChanged(@Nullable cursos: List<Curso>?) {
                listaCursos = cursos as ArrayList<Curso>
                updateView()
            }
        }
        cursosLiveData!!.observe(this, curso)
    }

    private fun initCursos(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CursoApi::class.java)
            val request = call.getCursosAll()
            val response = request.body() as ArrayList<Curso>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    allCursos = response
                    cursosLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los cursos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateView(){
        this.cursoAdapter = CursoAdapter(listaCursos, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerCursos)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = cursoAdapter
    }
}