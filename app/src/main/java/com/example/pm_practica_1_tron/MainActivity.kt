package com.example.pm_practica_1_tron

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var f1c1 : ImageView
    private lateinit var f1c2 : ImageView
    private lateinit var f1c3 : ImageView
    private lateinit var f2c1 : ImageView
    private lateinit var f2c2 : ImageView
    private lateinit var f2c3 : ImageView
    private lateinit var f3c1 : ImageView
    private lateinit var f3c2 : ImageView
    private lateinit var f3c3 : ImageView
    private lateinit var f4c1 : ImageView
    private lateinit var f4c2 : ImageView
    private lateinit var f4c3 : ImageView
    private lateinit var volver : ImageView

    private lateinit var vidas : TextView
    private lateinit var iniciar : ImageView

    private var estadoCartas = Array(12) {0}

    private var pares = intArrayOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6)
    private var parejaId = intArrayOf(-1, -2)
    private var parejaPos = intArrayOf(-1, -2)

    private var contadorPareja = 0
    private var puntosVida = 3
    private lateinit var casillas :  Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        f1c1 = findViewById(R.id.f1c1)
        f1c2 = findViewById(R.id.f1c2)
        f1c3 = findViewById(R.id.f1c3)
        f2c1 = findViewById(R.id.f2c1)
        f2c2 = findViewById(R.id.f2c2)
        f2c3 = findViewById(R.id.f2c3)
        f3c1 = findViewById(R.id.f3c1)
        f3c2 = findViewById(R.id.f3c2)
        f3c3 = findViewById(R.id.f3c3)
        f4c1 = findViewById(R.id.f4c1)
        f4c2 = findViewById(R.id.f4c2)
        f4c3 = findViewById(R.id.f4c3)
        volver = findViewById(R.id.iconoVolver)

        vidas = findViewById(R.id.vidas)
        iniciar = findViewById(R.id.iniciar)




        randomizarCartas()

        iniciar.setOnClickListener {
            cartasTapar(true)
            randomizarCartas()
            contadorPareja = 0
            puntosVida = 3
        }

        casillas = arrayOf(f1c1, f1c2, f1c3, f2c1, f2c2, f2c3, f3c1, f3c2, f3c3, f4c1, f4c2, f4c3)

        volver.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        for (i in 0 .. 11) {
            casillas[i].setOnClickListener {
                if (contadorPareja != 2 && puntosVida >= 0) {
                    if (sePuedeVoltear(i)) {
                        voltearCarta(i)
                        parejaId[contadorPareja] = pares[i] //Asigno el valor de la carta a la variable par para comparar si tienen el mismo
                        parejaPos[contadorPareja] = i
                        Log.v("debug2", "id Carta: "+parejaId[contadorPareja])
                        Log.v("debug2", "pos Carta: "+parejaPos[contadorPareja])
                        contadorPareja++

                    }
                }
                if (contadorPareja == 2) {
                    if (sonPareja()) {
                        Log.v("debug2", "contador con: " + contadorPareja)
                        contadorPareja = 0
                    } else {
                        object : CountDownTimer(1000, 1000){
                            override fun onTick(millisUntilFinished: Long) {
                                congelar(true)
                            }

                            override fun onFinish() {
                                voltearCarta(i)
                                puntosVida -= 1
                                vidas.text = "Vidas: $puntosVida"
                                Log.v("debug2", "contador sin pareja: " + contadorPareja)
                                cartasTapar(false)
                                contadorPareja = 0
                                congelar(false)
                            }
                        }.start()
                    }
                }

                if (puntosVida == 0) {
                    cartasTapar(false)
                    randomizarCartas()
                    Toast.makeText(this, "Se reinicia el juego", Toast.LENGTH_SHORT).show()
                    recreate()
                }
            }
        }
    }

    fun congelar(congelado: Boolean) {
        if (congelado) {
            for (carta in casillas) {
                carta.isClickable = false
            }
        } else {
            for (card in casillas) {
                card.isClickable = true
            }
        }
    }

    fun sePuedeVoltear(pos:Int):Boolean{
        return estadoCartas[pos] == 0
    }

    fun randomizarCartas(){
        pares.shuffle()
        puntosVida = 3
        vidas.text = "Vidas: $puntosVida"
    }

    fun voltearCarta(pos:Int){
        when(pares[pos]){
            1 -> casillas[pos].setImageResource(R.drawable.coco)
            2 -> casillas[pos].setImageResource(R.drawable.corvilo)
            3 -> casillas[pos].setImageResource(R.drawable.aquilino)
            4 -> casillas[pos].setImageResource(R.drawable.pando)
            5 -> casillas[pos].setImageResource(R.drawable.alfonso)
            6 -> casillas[pos].setImageResource(R.drawable.saponcio)
        }
        estadoCartas[pos] = 1
    }

    fun cartasTapar(haTerminado:Boolean){
        for (i in 0 .. 11){
            if (haTerminado){
                casillas[i].setImageResource(R.drawable.cartatrasera)
                estadoCartas[i] = 0
            } else if (estadoCartas[i] != 3){
                casillas[i].setImageResource(R.drawable.cartatrasera)
                estadoCartas[i] = 0
            }
        }
    }

    fun sonPareja(): Boolean {
        if(parejaId[0] == parejaId[1]){
            estadoCartas[parejaPos[0]] = 3
            estadoCartas[parejaPos[1]] = 3
        }
        contadorPareja = 0
        return parejaId[0] == parejaId[1]
    }

    fun reiniciarPartida(){

    }


}