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

class CiclosFragment : Fragment() {

    private var listaCiclos = ArrayList<Ciclo>()
    private lateinit var ciclosLiveData: MutableLiveData<List<Ciclo>>
    private lateinit var cicloAdapter: CicloAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.ciclosLiveData = MutableLiveData<List<Ciclo>>()
        this.cicloAdapter = CicloAdapter(listaCiclos, context!!)

        initCiclos()
        observer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ciclos, container, false)
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