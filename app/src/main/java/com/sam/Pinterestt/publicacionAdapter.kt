package com.sam.Pinterestt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.Pinterestt.databinding.ItemPublicacionBinding

class PublicacionAdapter(
    private val publicaciones: MutableList<Publicacion>,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder>() {

    inner class PublicacionViewHolder(val binding: ItemPublicacionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionViewHolder {
        val binding = ItemPublicacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PublicacionViewHolder, position: Int) {
        val publicacion = publicaciones[position]

        holder.binding.tvTitulo.text = publicacion.titulo
        holder.binding.tvContenido.text = publicacion.contenido

        holder.binding.btnEditar.setOnClickListener {
            onEdit(position)  // Llamamos al fragment para editar
        }

        holder.binding.btnEliminar.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount(): Int = publicaciones.size
}
