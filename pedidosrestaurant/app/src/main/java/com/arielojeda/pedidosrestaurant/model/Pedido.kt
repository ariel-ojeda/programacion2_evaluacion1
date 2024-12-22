package com.arielojeda.pedidosrestaurant.model

data class Pedido(
    val platillos: MutableList<Platillo>,
    var total: Int
)
