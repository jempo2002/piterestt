package com.sam.Pinterestt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sam.Pinterestt.databinding.ActivityComentariosBinding
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

class comentarios : AppCompatActivity() {
    private lateinit var binding: ActivityComentariosBinding
    private lateinit var comentariosAdapter: ComentariosAdapter
    private val listaComentarios = mutableListOf<Comentario>()
    private var publicacionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComentariosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Obtener el ID de la publicación
        publicacionId = intent.getIntExtra("publicacion_id", -1)
        if (publicacionId == -1) {
            finish()
            return
        }

        setupRecyclerView()
        setupListeners()
        cargarComentarios(publicacionId)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        comentariosAdapter = ComentariosAdapter(
            comentarios = listaComentarios,
            onEditClick = { comentario -> editarComentario(comentario) },
            onDeleteClick = { comentario -> eliminarComentario(comentario) }
        )

        binding.rvcomentarios.apply {
            layoutManager = LinearLayoutManager(this@comentarios)
            adapter = comentariosAdapter
        }
    }

    private fun setupListeners() {
        binding.btadd2.setOnClickListener {
            val textoComentario = binding.etcomentario.text.toString().trim()
            if (textoComentario.isNotEmpty()) {
                agregarComentario(textoComentario)
                binding.etcomentario.text.clear()
            }
        }
    }

    private fun cargarComentarios(publicacionId: Int) {
        // Aquí implementarías la carga desde tu base de datos
        // Por ahora, datos de ejemplo:
        // listaComentarios.addAll(baseDatos.getComentariosPorPublicacion(publicacionId))
        // comentariosAdapter.notifyDataSetChanged()
    }

    private fun agregarComentario(contenido: String) {
        val nuevoComentario = Comentario(
            publicacionId = publicacionId,
            contenido = contenido
        )
        listaComentarios.add(nuevoComentario)
        comentariosAdapter.notifyItemInserted(listaComentarios.size - 1)

        // Aquí guardarías en base de datos
        // baseDatos.insertarComentario(nuevoComentario)
    }

    private fun editarComentario(comentario: Comentario) {
        // Implementar lógica para editar
        // Probablemente abrir un Fragment o Dialog
    }

    private fun eliminarComentario(comentario: Comentario) {
        // Implementar confirmación y eliminación
    }
}