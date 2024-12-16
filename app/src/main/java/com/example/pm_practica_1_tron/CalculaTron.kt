package com.example.pm_practica_1_tron

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_practica_1_tron.R
import com.example.pm_practica_1_tron.databinding.ActivityCalculaTronBinding


class CalculaTron : AppCompatActivity() {
    private lateinit var bind: ActivityCalculaTronBinding
    private lateinit var fin : Intent

    private var min: Int = 0
    private var max: Int = 10

    //Para cuando pueda limitar lo que puede salir
    private var suma: Boolean = true
    private var resta: Boolean = true
    private var multiplicacion: Boolean = true

    private var cuentaPasada: String = ""
    private var cuentaActual: String = ""
    private var cuentaSiguiente: String = ""
    private var aciertos: Int = 0
    private var fallos: Int = 0

    private var num1: Int = 0
    private var num2: Int = 0
    private var num1Siguiente : Int = 0
    private var num2Siguiente : Int = 0
    private var operador : String = ""
    private var operadores = listOf("+", "-", "*")
    private var resultado : Int = 0
    private var resultadoSiguiente : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCalculaTronBinding.inflate(layoutInflater)
        fin = Intent(this, calcula_tron_historico::class.java)
        setContentView(bind.root)

        var segundos = bind.contador.text.toString().toLong() // Definir segundos fuera del CountDownTimer
        object : CountDownTimer(segundos * 10000, 1000) { // Convertir a milisegundos
            override fun onTick(millisUntilFinished: Long) {
                bind.contador.text = (millisUntilFinished / 1000).toString() // Actualizar el contador
            }

            override fun onFinish() {
                fin.putExtra("aciertos", aciertos)
                fin.putExtra("fallos", fallos)
                startActivity(fin)
            }
        }.start()

        cuentaSiguiente = generarCuenta()
        resultadoSiguiente = resultadoCuenta()
        bind.cuentaSiguiente.text = cuentaSiguiente
        cuentaActual = generarCuenta()
        resultado = resultadoCuenta()
        bind.cuentaActual.text = cuentaActual

        for (i in 0..9) {
            val button = when (i) {
                0 -> bind.b0
                1 -> bind.b1
                2 -> bind.b2
                3 -> bind.b3
                4 -> bind.b4
                5 -> bind.b5
                6 -> bind.b6
                7 -> bind.b7
                8 -> bind.b8
                9 -> bind.b9
                else -> null
            }
            button?.setOnClickListener {
                bind.input.append(i.toString())
            }
        }

        bind.borrarTodo.setOnClickListener {
            bind.input.setText("")
        }

        bind.borrarUltimo.setOnClickListener {
            borrarUltimoDigito()
        }

        bind.enviar.setOnClickListener {
            if (resultado == bind.input.text.toString().toInt()){
                sumarAciertos()
                estadoIconoAciertos(true)
            }else {
                sumarFallo()
                estadoIconoAciertos(false)
            }

            pasarTurno()
        }
    }

    fun generarCuenta():String{
            num1 = (min..max).random()
            operador = operadores.random()
            num2 = (min..max).random()


        var cuenta = num1.toString() + operador + num2.toString()

        return cuenta
    }

    fun resultadoCuenta(): Int {
        return when (operadores.indexOf(operador)) {
            0 -> num1 + num2
            1 -> num1 - num2
            2 -> num1 * num2
            else -> 0
        }
    }

    fun borrarUltimoDigito(){
        var texto = bind.input.text.toString()
        var textoAux = ""

        if (texto.isNotEmpty()) {
            textoAux = texto.substring(0, texto.length - 1)
        }
        bind.input.setText(textoAux)
    }

    fun pasarTurno(){
        cuentaPasada = bind.cuentaActual.text.toString()
        bind.cuentaAnterior.text = cuentaPasada

        cuentaActual = bind.cuentaSiguiente.text.toString()
        bind.cuentaActual.text = cuentaSiguiente
        resultado = resultadoCuenta()

        bind.cuentaSiguiente.text = generarCuenta()
        cuentaSiguiente = bind.cuentaSiguiente.text.toString()

        bind.input.text = ""
    }

    fun sumarAciertos(){
        aciertos++
        bind.aciertosNum.text = aciertos.toString()
    }

    fun sumarFallo(){
        fallos++
        bind.falloNum.text = fallos.toString()
    }

    fun estadoIconoAciertos(haAcertado:Boolean){
        if (haAcertado) {
            bind.iconoAcierto.setImageResource(R.drawable.baseline_check_24)
            bind.cuentaAnterior.setTextColor(ContextCompat.getColor(this, R.color.verdecito))
        }else {
            bind.iconoAcierto.setImageResource(R.drawable.baseline_clear_24)
            bind.cuentaAnterior.setTextColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_error))

        }
        }
}
