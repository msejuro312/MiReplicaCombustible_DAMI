package com.cibertec.mireplicacombustible

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.cibertec.mireplicacombustible.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalcular.setOnClickListener {
            calcularYMostrarResultado()
        }

        binding.editTextNombre.doAfterTextChanged {
            binding.inputLayoutNombre.error = null
        }

        binding.editTextDistancia.doAfterTextChanged {
            binding.inputLayoutDistancia.error = null
        }

        binding.editTextLitros.doAfterTextChanged {
            binding.inputLayoutLitros.error = null
        }

    }

    private fun leerValor (valor: String): Double?{
        val normalizado = valor.trim().replace(',','.')
        val numero = normalizado.toDoubleOrNull() ?: return null
        if(!numero.isFinite()) return null
        return if (numero > VALOR_MINIMO && numero <= VALOR_MAXIMO) numero else null
    }

    private fun calcularYMostrarResultado(){
        val nombre = binding.editTextNombre.text.toString().trim()
        val distancia = leerValor(binding.editTextDistancia.text.toString())
        val litros = leerValor (binding.editTextLitros.text.toString())
        var formularioValido = true

        if(nombre.isBlank()){
            binding.inputLayoutNombre.error = getString(R.string.error_nombre)
            formularioValido = false
        }

        if(distancia == null){
            binding.inputLayoutDistancia.error = getString(R.string.error_distancia)
            formularioValido = false
        }

        if(litros == null){
            binding.inputLayoutLitros.error = getString(R.string.error_litros)
            formularioValido = false
        }

        if(!formularioValido || distancia == null || litros == null){
            return
        }

        val rendimiento = calcularRendimiento (distancia, litros)
        val clasificacion = clasificarRendimiento (rendimiento)

        val intent = Intent(this, ResultadoActivity::class.java).apply {
            putExtra(ResultadoActivity.EXTRA_NOMBRE, nombre)
            putExtra(ResultadoActivity.EXTRA_RENDIMIENTO, rendimiento)
            putExtra(ResultadoActivity.EXTRA_CLASIFICACION, clasificacion)
        }

        startActivity(intent)

    }

    private fun calcularRendimiento(distancia: Double, litros: Double): Double{
        return distancia/litros
    }

    private fun clasificarRendimiento(rendimiento: Double): String{
        return when {
            rendimiento < LIMITE_MEDIO -> getString(R.string.clasificacion_rendimiento_bajo)
            rendimiento < LIMITE_BUENO ->getString(R.string.clasificacion_rendimiento_medio)
            rendimiento < LIMITE_EXCELENTE -> getString(R.string.clasificacion_buen_rendimiento)
            else -> getString(R.string.clasificacion_rendimiento_excelente)
        }
    }

    companion object {
        private const val VALOR_MINIMO = 0.0
        private const val VALOR_MAXIMO = 1_000_000.0
        private const val LIMITE_MEDIO = 8.0
        private const val LIMITE_BUENO = 12.0
        private const val LIMITE_EXCELENTE = 16.0




    }
}

