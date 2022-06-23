package com.example.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lab0405_frontend.Data.UsuarioApi
import com.example.lab04_frontend.Logica.IPAddress
import com.example.lab04_frontend.Logica.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var editTextCedulaLogin:EditText
    lateinit var editTextClaveLogin:EditText
    lateinit var btnLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextCedulaLogin = findViewById(R.id.editTextCedulaLogin)
        editTextClaveLogin = findViewById(R.id.editTextClaveLogin)

        btnLogin = findViewById(R.id.loginBtn)
        btnLogin.setOnClickListener{
            var cedula = Integer.parseInt(editTextCedulaLogin.text.toString())
            var clave = editTextClaveLogin.text.toString()

            val usuario = Usuario(cedula, clave, null)
            var dir = IPAddress()
            val retrofit = Retrofit.Builder().baseUrl(dir.getDireccion())
                .addConverterFactory(GsonConverterFactory.create()).build()

            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(UsuarioApi::class.java)
                val request = call.comprobar(usuario)
                val response = request.body() as Usuario
                if(request.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("Usuario", response)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}