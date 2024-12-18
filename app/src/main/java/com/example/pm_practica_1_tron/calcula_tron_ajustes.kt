package com.example.pm_practica_1_tron

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_practica_1_tron.R
import com.example.pm_practica_1_tron.databinding.ActivityCalculaTronAjustesBinding

class calcula_tron_ajustes : AppCompatActivity() {

    private lateinit var bind: ActivityCalculaTronAjustesBinding
    private lateinit var shared : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = ActivityCalculaTronAjustesBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        shared = getSharedPreferences("ajustes", MODE_PRIVATE)

        bind.enviar.setOnClickListener {
            val editor = shared.edit()

            editor.putInt("minimo", bind.min.text.toString().toInt())
            editor.putInt("maximo", bind.max.text.toString().toInt())
            editor.putInt("cuentaatras", bind.cuentaAtras.text.toString().toInt())
            editor.putBoolean("suma", bind.suma.isChecked)
            editor.putBoolean("resta", bind.resta.isChecked)
            editor.putBoolean("multiplicacion", bind.multiplicacion.isChecked)

            editor.apply() //Aplica los cambios
        }
    }
}