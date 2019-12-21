package com.balzhoyt.cafezh.muestras.ui.inicio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.balzhoyt.cafezh.muestras.CapturarFotoActivity
import com.balzhoyt.cafezh.muestras.R

class CapturarImagenInicioFragment : Fragment() {

    private lateinit var capturarImagenViewModel: CapturarImagenInicioViewModel


    private fun capturarFoto(url:String) {
        val i = Intent(this@CapturarImagenInicioFragment.context, CapturarFotoActivity::class.java)
        i.putExtra("urlFoto", url)
        startActivity(i)
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        capturarImagenViewModel =
                ViewModelProviders.of(this).get(CapturarImagenInicioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_inicio_capturas, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)

        val btnHojasSanas:CardView=root.findViewById(R.id.cvHojasSanas)
        btnHojasSanas.setOnClickListener { capturarFoto("Hojas Sanas") }

        val btnRoya:CardView=root.findViewById(R.id.cvHojasRoya)
        btnRoya.setOnClickListener { capturarFoto("Enfermedad Roya") }

        val btnManchaHierro:CardView=root.findViewById(R.id.cvHojasManchaHierro)
        btnManchaHierro.setOnClickListener { capturarFoto("Enfermedad Mancha de Hierro") }

        val btnOjodeGallo:CardView=root.findViewById(R.id.cvHojasOjoGallo)
        btnOjodeGallo.setOnClickListener { capturarFoto("Enfermedad Ojo de Gallo") }

        val btnMagnesio:CardView=root.findViewById(R.id.cvMagnesio)
        btnMagnesio.setOnClickListener { capturarFoto("Deficit de Magnesio") }

        val btnNitrogeno:CardView=root.findViewById(R.id.cvNitrogeno)
        btnNitrogeno.setOnClickListener { capturarFoto("Deficit de Nitrogeno") }

        val btnFosforo:CardView=root.findViewById(R.id.cvFosforo)
        btnFosforo.setOnClickListener { capturarFoto("Deficit de Fosforo") }

        val btnAzufre:CardView=root.findViewById(R.id.cvAzufre)
        btnAzufre.setOnClickListener { capturarFoto("Deficit de Azufre") }

        capturarImagenViewModel.text.observe(this, Observer {
           // textView.text = it
        })
        return root
    }
}