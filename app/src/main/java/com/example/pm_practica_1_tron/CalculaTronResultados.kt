package com.example.pm_practica_1_tron

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_practica_1_tron.databinding.ActivityCalculaTronAjustesBinding
import com.example.pm_practica_1_tron.databinding.ActivityCalculaTronResultadosBinding

private var aciertostotales = 0
private var fallostotales = 0

class CalculaTronResultados : AppCompatActivity() {
    private lateinit var bind: ActivityCalculaTronResultadosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCalculaTronResultadosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var aciertos = intent.getIntExtra("aciertos", 0)
        bind.aciCantNum.text = aciertos.toString()
        aciertostotales += aciertos
        bind.numAciertosTotal.text = aciertostotales.toString()

        var fallos = intent.getIntExtra("fallos", 0)
        bind.errCantNum.text = fallos.toString()
        fallostotales+= fallos
        bind.numErroresTotal.text = fallostotales.toString()

        var porcentajeAciertos = if ((aciertos+fallos) == 0) 0 else (aciertos*100) / (aciertos+fallos)
        bind.porcentajeAciertos.text = "Porcentaje de aciertos "+porcentajeAciertos+"%"

        var porcentajeAciertosTotales = if ((aciertostotales+ fallostotales) == 0) 0 else (aciertostotales*100) / (aciertostotales+ fallostotales)
        bind.porcentajeAciertosTotal.text = "Porcentaje de aciertos "+porcentajeAciertosTotales+"%"

        bind.iconoSup.setOnClickListener{
            val intent = Intent(this, MenuPrincipal::class. java)
            startActivity(intent)
        }
    }
}