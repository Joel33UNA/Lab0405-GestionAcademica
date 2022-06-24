package com.example.View.Fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Data.CicloApi
import com.example.View.Adapter.CicloAdapter
import com.example.View.R
import com.example.lab04_frontend.Logica.Ciclo
import com.example.lab04_frontend.Logica.IPAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class CiclosFragment : Fragment() {

    private var allCiclos = ArrayList<Ciclo>()
    private var listaCiclos = ArrayList<Ciclo>()
    private lateinit var ciclosLiveData: MutableLiveData<List<Ciclo>>
    private lateinit var cicloAdapter: CicloAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ciclos, container, false)

        this.ciclosLiveData = MutableLiveData<List<Ciclo>>()
        this.cicloAdapter = CicloAdapter(listaCiclos, context!!)
        initCiclos()
        observer()

        val searchView = view!!.findViewById<SearchView>(R.id.searchViewCiclos)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            val auxCiclos = ArrayList<Ciclo>()

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchText = p0!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    auxCiclos.clear()
                    allCiclos.forEach{
                        if(it.anio.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            auxCiclos.add(it)
                        }
                    }
                    ciclosLiveData!!.value = auxCiclos
                }
                else{
                    auxCiclos.clear()
                    auxCiclos.addAll(allCiclos)
                    ciclosLiveData.value = auxCiclos
                }
                return false
            }
        })

        return view
    }

    private fun observer() {
        val ciclo: Observer<List<Ciclo>> = object : Observer<List<Ciclo>> {
            @Override
            override fun onChanged(@Nullable ciclos: List<Ciclo>?) {
                listaCiclos = ciclos as ArrayList<Ciclo>
                updateView()
            }
        }
        ciclosLiveData!!.observe(this, ciclo)
    }

    private fun initCiclos(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(CicloApi::class.java)
            val request = call.getCiclosAll()
            val response = request.body() as ArrayList<Ciclo>
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    allCiclos = response
                    ciclosLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar los ciclos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateView(){
        this.cicloAdapter = CicloAdapter(listaCiclos, context!!)
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerCiclos)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context!!))
        recyclerView.adapter = cicloAdapter
    }
}