package com.sam.Pinterestt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sam.Pinterestt.R
import com.sam.Pinterestt.databinding.ActivityMainBinding
import com.sam.Pinterestt.registro
import com.sam.Pinterestt.visualizarActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val validarUsuario = mutableListOf(
            Pair("juan", "123")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializar()
        evento()
    }

    fun inicializar() {
        // Set click listener for the register button
        binding.btregistro.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }

        supportActionBar?.title = getString(R.string.main)
    }

    fun evento() {
        // Set click listener for the login button
        binding.btlogin.setOnClickListener {
            val escribirEmail = binding.ethuser.text.toString()
            val escribirPassword = binding.ethpass.text.toString()

            if (validarUsuario.contains(Pair(escribirEmail, escribirPassword))) {
                val intent = Intent(this, visualizarActivity::class.java)
                startActivity(intent)
                finish() // Close the login activity
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

}