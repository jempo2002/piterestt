package com.sam.Pinterestt

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.Pinterestt.databinding.ActivityComentariosBinding

class comentarios : AppCompatActivity() {

    private var _binding: ActivityComentariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var comentariosAdapter: ComentariosAdapter
    private val listaComentarios = mutableListOf<Comentario>()
    private var publicacionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityComentariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID de la publicación
        publicacionId = intent.getIntExtra("publicacion_id", -1)
        if (publicacionId == -1) {
            Toast.makeText(this, "Error: ID de publicación no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerView()
        setupListeners()
        cargarComentarios(publicacionId)
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
            } else {
                Toast.makeText(this, "Escribe un comentario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarComentarios(publicacionId: Int) {
        // Simulación de datos iniciales
        listaComentarios.addAll(
            listOf(
                Comentario(id = 1, publicacionId = publicacionId, contenido = "Comentario de ejemplo 1"),
                Comentario(id = 2, publicacionId = publicacionId, contenido = "Comentario de ejemplo 2")
            )
        )
        comentariosAdapter.notifyDataSetChanged()
    }

    private fun agregarComentario(contenido: String) {
        val nuevoComentario = Comentario(
            id = (listaComentarios.maxOfOrNull { it.id } ?: 0) + 1,
            publicacionId = publicacionId,
            contenido = contenido
        )
        listaComentarios.add(nuevoComentario)
        comentariosAdapter.notifyItemInserted(listaComentarios.size - 1)
        binding.rvcomentarios.scrollToPosition(listaComentarios.size - 1)
        Toast.makeText(this, "Comentario agregado", Toast.LENGTH_SHORT).show()
    }

    private fun editarComentario(comentario: Comentario) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_edit_coment, null)

        val etComentario = dialogView.findViewById<EditText>(R.id.eteditcoment)
        etComentario.setText(comentario.contenido)

        AlertDialog.Builder(this)
            .setTitle("Editar comentario")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nuevoContenido = etComentario.text.toString().trim()
                if (nuevoContenido.isNotEmpty()) {
                    val index = listaComentarios.indexOf(comentario)
                    if (index != -1) {
                        listaComentarios[index] = comentario.copy(contenido = nuevoContenido)
                        comentariosAdapter.notifyItemChanged(index)
                        Toast.makeText(this, "Comentario actualizado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarComentario(comentario: Comentario) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar comentario")
            .setMessage("¿Estás seguro de que quieres eliminar este comentario?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                val index = listaComentarios.indexOf(comentario)
                if (index != -1) {
                    listaComentarios.removeAt(index)
                    comentariosAdapter.notifyItemRemoved(index)
                    Toast.makeText(this, "Comentario eliminado", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}