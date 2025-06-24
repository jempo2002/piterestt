package com.sam.Pinterestt

import android.content.Intent
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
            onEdit(position)
        }

        holder.binding.btnEliminar.setOnClickListener {
            onDelete(position)
        }

        holder.binding.btncomentar.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, comentarios::class.java).apply {
                putExtra("publicacion_id", publicacion.idPublicacion ?: -1) //valor por defecto si es null
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = publicaciones.size
}