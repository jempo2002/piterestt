package com.sam.Pinterestt

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.Pinterestt.databinding.FragmentPublicacionBinding

class pinteresttFragment : Fragment() {

    private var _binding: FragmentPublicacionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PublicacionAdapter
    private val publicaciones = mutableListOf<Publicacion>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        adapter = PublicacionAdapter(
            publicaciones,
            onEdit = { position -> mostrarDialogoEditar(position) },
            onDelete = { position ->
                publicaciones.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )

        binding.rvPublicaciones.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@pinteresttFragment.adapter
        }
    }

    private fun setupAddButton() {
        binding.btaddfp.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val contenido = binding.etContenido.text.toString()

            if (titulo.isNotEmpty() && contenido.isNotEmpty()) {
                val nuevaPublicacion = Publicacion(
                    idPublicacion = Publicacion.generarNuevoId(),
                    titulo = titulo,
                    contenido = contenido
                )
                publicaciones.add(nuevaPublicacion)
                adapter.notifyItemInserted(publicaciones.size - 1)
                limpiarCampos()
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnsalirfp.setOnClickListener {
            val intent = Intent(requireContext(), visualizarActivity::class.java)
            startActivity(intent)
            requireActivity() .finish() //finaliza la actividad
        }

    }

    private fun limpiarCampos() {
        binding.etTitulo.text.clear()
        binding.etContenido.text.clear()
    }

    private fun mostrarDialogoEditar(position: Int) {
        val publicacion = publicaciones[position]
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_publicacion, null)

        val etTitulo = dialogView.findViewById<EditText>(R.id.etEditTitulo)
        val etContenido = dialogView.findViewById<EditText>(R.id.etEditContenido)

        etTitulo.setText(publicacion.titulo)
        etContenido.setText(publicacion.contenido)

        AlertDialog.Builder(requireContext())
            .setTitle("Editar publicación")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                publicacion.titulo = etTitulo.text.toString()
                publicacion.contenido = etContenido.text.toString()
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}