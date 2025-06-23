package com.sam.Pinterestt


import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.Pinterestt.databinding.FragmentPatreonBinding

class PatreonFragment : Fragment() {

    private var _binding: FragmentPatreonBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PublicacionAdapter
    private val publicaciones = mutableListOf<Publicacion>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPatreonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PublicacionAdapter(
            publicaciones,
            onEdit = { position -> mostrarDialogoEditar(position) },
            onDelete = { position ->
                publicaciones.removeAt(position)
                adapter.notifyDataSetChanged()

            }
        )

        binding.rvPublicaciones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPublicaciones.adapter = adapter


        adapter.notifyDataSetChanged()


        binding.btadd2.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val contenido = binding.etContenido.text.toString()
            if (titulo.isNotEmpty() && contenido.isNotEmpty()) {
                publicaciones.add(Publicacion(titulo, contenido))
                adapter.notifyItemInserted(publicaciones.size - 1)
                binding.etTitulo.text.clear()
                binding.etContenido.text.clear()
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
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
