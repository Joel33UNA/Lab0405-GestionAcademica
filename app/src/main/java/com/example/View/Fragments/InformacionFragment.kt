package com.example.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.Data.EstudianteApi
import com.example.Data.ProfesorApi
import com.example.View.R
import com.example.lab04_frontend.Logica.Estudiante
import com.example.lab04_frontend.Logica.IPAddress
import com.example.lab04_frontend.Logica.Profesor
import com.example.lab04_frontend.Logica.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InformacionFragment : Fragment() {

    private lateinit var usuario: Usuario
    private lateinit var estudianteLiveData: MutableLiveData<Estudiante>
    private lateinit var profesorLiveData: MutableLiveData<Profesor>
    private var estudiante = Estudiante()
    private var profesor = Profesor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.usuario = activity!!.intent.extras!!.getSerializable("Usuario") as Usuario
        this.estudianteLiveData = MutableLiveData<Estudiante>()
        this.profesorLiveData = MutableLiveData<Profesor>()

        if(usuario.rol.equals("estudiante")){
            initEstudiante()
            observerEstudiante()
        }
        else if(usuario.rol.equals("profesor")){
            initProfesor()
            observerProfesor()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacion, container, false)
    }

    private fun initProfesor(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        try{
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(ProfesorApi::class.java)
                val request = call.get(usuario.cedula)
                val response = request.body() as Profesor
                if(request.isSuccessful){
                    withContext(Dispatchers.Main) {
                        profesorLiveData!!.value = response
                    }
                }
            }
        }
        catch(exception:Exception){
            Toast.makeText(context!!, "Error al mostrar el profesor", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initEstudiante(){
        var dir = IPAddress()
        val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
            .addConverterFactory(GsonConverterFactory.create()).build()

        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(EstudianteApi::class.java)
            val request = call.get(usuario.cedula)
            val response = request.body() as Estudiante
            if(request.isSuccessful){
                withContext(Dispatchers.Main) {
                    estudianteLiveData!!.value = response
                }
            }else{
                Toast.makeText(context!!, "Error al mostrar el estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerEstudiante() {
        val observer: Observer<Estudiante> = object : Observer<Estudiante> {
            @Override
            override fun onChanged(@Nullable estudiante: Estudiante) {
                this@InformacionFragment.estudiante = estudiante
                updateView()
            }
        }
        estudianteLiveData!!.observe(this, observer)
    }

    private fun observerProfesor() {
        val observer: Observer<Profesor> = object : Observer<Profesor> {
            @Override
            override fun onChanged(@Nullable profesor: Profesor) {
                this@InformacionFragment.profesor = profesor
                updateView()
            }
        }
        profesorLiveData!!.observe(this, observer)
    }

    private fun updateView() {
        val textViewCedula = view!!.findViewById<TextView>(R.id.textViewCedulaText)
        val textViewNombre = view!!.findViewById<TextView>(R.id.textViewCedula)
        val textViewTelefono = view!!.findViewById<TextView>(R.id.textViewTelefono)
        val textViewCorreo = view!!.findViewById<TextView>(R.id.textViewCorreo)
        val textViewFechaNacimiento = view!!.findViewById<TextView>(R.id.textViewFechaNacimiento)
        val textViewFechaNacimientoText = view!!.findViewById<TextView>(R.id.textViewFechaNacimientoText)
        val textViewCarreraText = view!!.findViewById<TextView>(R.id.textViewCarreraText)
        val textViewCarrera = view!!.findViewById<TextView>(R.id.textViewCedula)
        val textViewTituloCarreraText = view!!.findViewById<TextView>(R.id.textViewTituloCarreraText)
        val textViewTituloCarrera = view!!.findViewById<TextView>(R.id.textViewTituloCarrera)

        if(usuario.rol.equals("profesor")){
            textViewCedula.setText(profesor.cedula.toString())
            textViewNombre.setText(profesor.nombre.toString())
            textViewTelefono.setText(profesor.telefono.toString())
            textViewCorreo.setText(profesor.email.toString())
            textViewFechaNacimiento.isVisible = false
            textViewFechaNacimientoText.isVisible = false
            textViewCarreraText.isVisible = false
            textViewCarrera.isVisible = false
            textViewTituloCarreraText.isVisible = false
            textViewTituloCarrera.isVisible = false
        }
        else if(usuario.rol.equals("estudiante")){
            textViewCedula.setText(estudiante.cedula.toString())
            textViewNombre.setText(estudiante.nombre.toString())
            textViewTelefono.setText(estudiante.telefono.toString())
            textViewCorreo.setText(estudiante.email.toString())
            textViewFechaNacimiento.setText(estudiante.fechaNacimiento.toString())
            textViewCarrera.setText(estudiante.carrera!!.nombre.toString())
            textViewTituloCarrera.setText(estudiante.carrera!!.titulo.toString())
            textViewFechaNacimiento.isVisible = true
            textViewFechaNacimientoText.isVisible = true
            textViewCarreraText.isVisible = true
            textViewCarrera.isVisible = true
            textViewTituloCarreraText.isVisible = true
            textViewTituloCarrera.isVisible = true
        }
    }
}