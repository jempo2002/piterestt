package com.sam.Pinterestt

data class Publicacion(
        val idPublicacion: Int,
        var titulo: String,
        var contenido: String
) {
        companion object {
                private var ultimoId = 0
                fun generarNuevoId(): Int = ++ultimoId
        }
}
