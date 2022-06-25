package com.example.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.View.Fragments.*
import com.example.lab04_frontend.Logica.Usuario
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_menu.setNavigationItemSelectedListener(this)

        var bundle = intent.extras
        usuario =  bundle?.getSerializable("Usuario") as Usuario

        val navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val header = navigationView?.getHeaderView(0)
        header?.findViewById<TextView>(R.id.nav_header_nombre)?.text = usuario.cedula.toString()

        changeFragment(HomeFragment())
        setToolbarTitle("Bienvenido(a)")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId){
            R.id.nav_home -> {
                changeFragment(HomeFragment())
                setToolbarTitle("Bienvenido(a)")
            }
            R.id.nav_informacion -> {
                changeFragment(InformacionFragment())
                setToolbarTitle("Información")
            }
            R.id.nav_ciclos -> {
                changeFragment(CiclosFragment())
                setToolbarTitle("Ciclos")
            }
            R.id.nav_carreras -> {
                changeFragment(CarrerasFragment())
                setToolbarTitle("Carreras")
            }
            R.id.nav_cursos -> {
                changeFragment(CursosFragment())
                setToolbarTitle("Cursos")
            }
            R.id.nav_estudiantes -> {
                changeFragment(EstudiantesFragment())
                setToolbarTitle("Estudiantes")
            }
            R.id.nav_profesores -> {
                changeFragment(ProfesoresFragment())
                setToolbarTitle("Profesores")
            }
            R.id.nav_oferta_academica -> {
                changeFragment(OfertaAcademicaFragment())
                setToolbarTitle("Oferta académica")
            }
            R.id.grupos_a_cargo -> {
                changeFragment(MatriculasFragment())
                setToolbarTitle("Matriculas")
            }
            R.id.nav_historial_academico -> {
                changeFragment(HistorialAcademicoFragment())
                setToolbarTitle("Historial académico")
            }
            R.id.nav_logout -> {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        var navigationView = findViewById<NavigationView>(R.id.nav_menu)
        var nav_menu = navigationView.menu
        if(usuario.rol == "administrador"){
            nav_menu.findItem(R.id.nav_home).isVisible = true
            nav_menu.findItem(R.id.nav_informacion).isVisible = false
            nav_menu.findItem(R.id.nav_ciclos).isVisible = true
            nav_menu.findItem(R.id.nav_carreras).isVisible = true
            nav_menu.findItem(R.id.nav_cursos).isVisible = true
            nav_menu.findItem(R.id.nav_estudiantes).isVisible = true
            nav_menu.findItem(R.id.nav_profesores).isVisible = true
            nav_menu.findItem(R.id.nav_oferta_academica).isVisible = true
            nav_menu.findItem(R.id.grupos_a_cargo).isVisible = false
            nav_menu.findItem(R.id.nav_historial_academico).isVisible = false
        }
        else if(usuario.rol == "profesor"){
            nav_menu.findItem(R.id.nav_home).isVisible = false
            nav_menu.findItem(R.id.nav_informacion).isVisible = true
            nav_menu.findItem(R.id.nav_ciclos).isVisible = false
            nav_menu.findItem(R.id.nav_carreras).isVisible = false
            nav_menu.findItem(R.id.nav_cursos).isVisible = false
            nav_menu.findItem(R.id.nav_estudiantes).isVisible = false
            nav_menu.findItem(R.id.nav_profesores).isVisible = false
            nav_menu.findItem(R.id.nav_oferta_academica).isVisible = false
            nav_menu.findItem(R.id.grupos_a_cargo).isVisible = true
            nav_menu.findItem(R.id.nav_historial_academico).isVisible = false
        }
        else if(usuario.rol == "estudiante"){
            nav_menu.findItem(R.id.nav_home).isVisible = false
            nav_menu.findItem(R.id.nav_informacion).isVisible = true
            nav_menu.findItem(R.id.nav_ciclos).isVisible = false
            nav_menu.findItem(R.id.nav_carreras).isVisible = false
            nav_menu.findItem(R.id.nav_cursos).isVisible = false
            nav_menu.findItem(R.id.nav_estudiantes).isVisible = false
            nav_menu.findItem(R.id.nav_profesores).isVisible = false
            nav_menu.findItem(R.id.nav_oferta_academica).isVisible = false
            nav_menu.findItem(R.id.grupos_a_cargo).isVisible = false
            nav_menu.findItem(R.id.nav_historial_academico).isVisible = true
        }
        else{
            nav_menu.findItem(R.id.nav_informacion).isVisible = false
            nav_menu.findItem(R.id.nav_home).isVisible = false
            nav_menu.findItem(R.id.nav_ciclos).isVisible = false
            nav_menu.findItem(R.id.nav_carreras).isVisible = false
            nav_menu.findItem(R.id.nav_cursos).isVisible = false
            nav_menu.findItem(R.id.nav_estudiantes).isVisible = true
            nav_menu.findItem(R.id.nav_profesores).isVisible = false
            nav_menu.findItem(R.id.nav_oferta_academica).isVisible = false
            nav_menu.findItem(R.id.grupos_a_cargo).isVisible = false
            nav_menu.findItem(R.id.nav_historial_academico).isVisible = false
        }
        return true
    }

    fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }
    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}