package com.sam.Pinterestt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sam.Pinterestt.databinding.RegistroBinding
import com.sam.pinterestt.MainActivity

class registro : AppCompatActivity() {

    private lateinit var binding: RegistroBinding

//    private lateinit var nuevousur: EditText
//    private lateinit var nuevacontra: EditText
//    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        nuevousur = findViewById(R.id.nuevousur)
//        nuevacontra = findViewById(R.id.nuevacontra)
//        btnRegister = findViewById(R.id.btnRegister)

//        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        binding.bt2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // hace que finaliza esta actividad para que pueda retroceder
        }

        binding.bt1.setOnClickListener {
            val nuevoU = binding.ethuser.text.toString()
            val nuevaP = binding.ethpass.text.toString()

            if(nuevoU.isNotEmpty() && nuevaP.isNotEmpty()) {
                MainActivity.validarUsuario.add(Pair(nuevoU, nuevaP))
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

                // es para volver a la pantalla de login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()

            }

        }


    }
}
