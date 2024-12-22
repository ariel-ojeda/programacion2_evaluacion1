package com.arielojeda.pedidosrestaurant.model

data class Platillo(
    val nombre: String,
    val precio: Int,
    var cantidad: Int
)
