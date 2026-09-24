package com.cibertec.mireplicacombustible

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.mireplicacombustible.databinding.ActivityResultadoBinding
class ResultadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mostrarResultado()

        binding.buttonVolver.setOnClickListener {
            finish()
        }

    }

    private fun mostrarResultado(){
        val nombre = intent.getStringExtra(EXTRA_NOMBRE)
            ?: getString(R.string.valor_no_disponible)
        val rendimiento = intent.getDoubleExtra(EXTRA_RENDIMIENTO, RENDIMIENTO_POR_DEFECTO)
        val clasificacion = intent.getStringExtra(EXTRA_CLASIFICACION)
            ?: getString(R.string.valor_no_disponible)

        binding.textViewNombreResultado.text =
            getString(R.string.resultado_nombre_formato,nombre)
        binding.textViewRendimientoResultado.text =
            getString(R.string.resultado_rendimiento_formato,rendimiento)
        binding.textViewClasificacionResultado.text =
            clasificacion


    }

    companion object {
        const val EXTRA_NOMBRE = "extra_nombre"
        const val EXTRA_RENDIMIENTO = "extra_rendimiento"
        const val EXTRA_CLASIFICACION = "extra_clasificacion"
        private const val RENDIMIENTO_POR_DEFECTO = 0.0
    }
}