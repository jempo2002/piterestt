package com.sam.Pinterestt


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sam.Pinterestt.databinding.ActivityVisualizarBinding
import com.sam.Pinterestt.MainActivity

class visualizarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVisualizarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btadd1.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainerView)

            if (binding.fragmentContainerView.visibility == View.VISIBLE && currentFragment is pinteresttFragment) {
                // Si ya está visible, lo removemos y ocultamos el contenedor
                fragmentTransaction.remove(currentFragment)
                fragmentTransaction.commit()
                binding.fragmentContainerView.visibility = View.GONE
            } else {
                // Si no está visible, lo mostramos
                fragmentTransaction.replace(R.id.fragmentContainerView, pinteresttFragment())
                fragmentTransaction.commit()
                binding.fragmentContainerView.visibility = View.VISIBLE
            }
        }


        binding.btnsalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
