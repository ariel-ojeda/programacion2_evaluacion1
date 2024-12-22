package com.arielojeda.pedidosrestaurant

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.arielojeda.pedidosrestaurant.model.Pedido
import com.arielojeda.pedidosrestaurant.model.Platillo
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var pedido: Pedido
    private lateinit var platillos: List<Platillo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        platillos = listOf(
            Platillo("Cazuela", 10000, 0),
            Platillo("Pastel de Choclo", 12000, 0)
        )
        pedido = Pedido(platillos.toMutableList(), 0)

        val switchPropina = findViewById<Switch>(R.id.switch1)
        val txtTotalFinal = findViewById<TextView>(R.id.txtValorFinal)
        val txtTotalComida = findViewById<TextView>(R.id.txtTotalCantidadComida)
        val txtValorCantidadPropina = findViewById<TextView>(R.id.txtValorCantidadPropina)

        // Cazuela
        val editCazuela = findViewById<EditText>(R.id.inputCcazuela)
        val txtTotalCantidadCazuela = findViewById<TextView>(R.id.txtTotalCantidadCazuela)

        editCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                platillos[0].cantidad = cantidad
                txtTotalCantidadCazuela.text = formatCurrency(platillos[0].precio * platillos[0].cantidad)
                actualizarTotales(txtTotalComida, txtTotalFinal, txtValorCantidadPropina, switchPropina.isChecked)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Pastel de Choclo
        val editPastel = findViewById<EditText>(R.id.inputCpastel)
        val txtCantidadTotalPastel = findViewById<TextView>(R.id.txtCantidadTotalPastel)

        editPastel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                platillos[1].cantidad = cantidad
                txtCantidadTotalPastel.text = formatCurrency(platillos[1].precio * platillos[1].cantidad)
                actualizarTotales(txtTotalComida, txtTotalFinal, txtValorCantidadPropina, switchPropina.isChecked)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            actualizarTotales(txtTotalComida, txtTotalFinal, txtValorCantidadPropina, isChecked)
        }
    }

    private fun actualizarTotales(
        txtTotalComida: TextView,
        txtTotalFinal: TextView,
        txtValorCantidadPropina: TextView,
        incluirPropina: Boolean
    ) {
        val subtotal = platillos.sumOf { it.precio * it.cantidad }
        val propina = if (incluirPropina) subtotal * 0.1 else 0.0
        val total = subtotal + propina

        txtTotalComida.text = formatCurrency(subtotal)
        txtValorCantidadPropina.text = formatCurrency(propina.toInt())
        txtTotalFinal.text = formatCurrency(total.toInt())
    }

    private fun formatCurrency(amount: Int): String {
        val formato = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 0
        }
        return formato.format(amount)
    }
}
