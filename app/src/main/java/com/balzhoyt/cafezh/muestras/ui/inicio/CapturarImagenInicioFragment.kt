package com.balzhoyt.cafezh.muestras.ui.inicio

import android.content.Intent
import android.content.SharedPreferences
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
import com.balzhoyt.cafezh.muestras.utils.PrefsApp
import kotlinx.android.synthetic.main.fragment_inicio_capturas.*
import android.os.Build.VERSION

import android.os.Build.VERSION.SDK_INT




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



        capturarImagenViewModel = ViewModelProviders.of(this).get(CapturarImagenInicioViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_inicio_capturas, container, false)

        val txtTotalHojaSana: TextView = root.findViewById(R.id.txtTotalHojaSana)
        txtTotalHojaSana.text=PrefsApp.prefs.c_hojas_sanas.toString()

        val txtTotalRoya: TextView = root.findViewById(R.id.txtTotalRoya)
        txtTotalRoya.text=PrefsApp.prefs.c_roya.toString()

        val txtTotalManchaHierro: TextView = root.findViewById(R.id.txtTotalManchaHierro)
        txtTotalManchaHierro.text=PrefsApp.prefs.c_mancha_hierro.toString()

        val txtTotalMagnesio: TextView = root.findViewById(R.id.txtTotalMagnesio)
        txtTotalMagnesio.text=PrefsApp.prefs.c_deficit_magnesio.toString()

        val txtTotalNitrogeno: TextView = root.findViewById(R.id.txtTotalNitrogeno)
        txtTotalNitrogeno.text=PrefsApp.prefs.c_deficit_magnesio.toString()

        val txtTotalFosforo: TextView = root.findViewById(R.id.txtTotalFosforo)
        txtTotalFosforo.text=PrefsApp.prefs.c_deficit_fosforo.toString()

        val txtTotalOjoGallo: TextView = root.findViewById(R.id.txtTotalOjoGallo)
        txtTotalOjoGallo.text=PrefsApp.prefs.c_ojo_gallo.toString()

        val txtTotalAzufre: TextView = root.findViewById(R.id.txtTotalAzufre)
        txtTotalAzufre.text=PrefsApp.prefs.c_deficit_azufre.toString()


        val btnHojasSanas:CardView=root.findViewById(R.id.cvHojasSanas)
        btnHojasSanas.setOnClickListener {capturarFoto("Hojas Sanas")}

        val btnRoya:CardView=root.findViewById(R.id.cvHojasRoya)
        btnRoya.setOnClickListener {capturarFoto("Enfermedad Roya")}

        val btnManchaHierro:CardView=root.findViewById(R.id.cvHojasManchaHierro)
        btnManchaHierro.setOnClickListener {  capturarFoto("Enfermedad Mancha de Hierro")}

        val btnOjodeGallo:CardView=root.findViewById(R.id.cvHojasOjoGallo)
        btnOjodeGallo.setOnClickListener { capturarFoto("Enfermedad Ojo de Gallo") }

        val btnMagnesio:CardView=root.findViewById(R.id.cvMagnesio)
        btnMagnesio.setOnClickListener { capturarFoto("Deficit de Magnesio")}

        val btnNitrogeno:CardView=root.findViewById(R.id.cvNitrogeno)
        btnNitrogeno.setOnClickListener {capturarFoto("Deficit de Nitrogeno")}

        val btnFosforo:CardView=root.findViewById(R.id.cvFosforo)
        btnFosforo.setOnClickListener { capturarFoto("Deficit de Fosforo")}

        val btnAzufre:CardView=root.findViewById(R.id.cvAzufre)
        btnAzufre.setOnClickListener { capturarFoto("Deficit de Azufre")}

        capturarImagenViewModel.text.observe(this, Observer {
           // textView.text = it
        })
        return root
    }
}