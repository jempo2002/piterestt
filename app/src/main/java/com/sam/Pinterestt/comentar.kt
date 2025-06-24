package com.sam.Pinterestt

import java.io.Serializable

data class Comentario(
    val id: Int = 0,
    val publicacionId: Int,
    var contenido: String,
    val fecha: Long = System.currentTimeMillis()
) : Serializable {

    constructor(publicacionId: Int, contenido: String) : this(
        id = generarNuevoId(),
        publicacionId = publicacionId,
        contenido = contenido
    )

    companion object {
        private var contadorId = 0
        fun generarNuevoId(): Int {
            contadorId++
            return contadorId
        }
    }
}