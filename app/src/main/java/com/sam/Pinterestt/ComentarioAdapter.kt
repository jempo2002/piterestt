package com.sam.Pinterestt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.Pinterestt.databinding.ItemComentarioBinding

class ComentariosAdapter(
    private val comentarios: MutableList<Comentario>,
    private val onEditClick: (Comentario) -> Unit,
    private val onDeleteClick: (Comentario) -> Unit
) : RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>() {

    inner class ComentarioViewHolder(private val binding: ItemComentarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comentario: Comentario) {
            binding.tvcomentarioic.text = comentario.contenido
            binding.btneditaric.setOnClickListener { onEditClick(comentario) }
            binding.btneliminaric.setOnClickListener { onDeleteClick(comentario) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val binding = ItemComentarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ComentarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    override fun getItemCount(): Int = comentarios.size
}