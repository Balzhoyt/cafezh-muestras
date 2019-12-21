package com.balzhoyt.cafezh.muestras.ui.produccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.balzhoyt.cafezh.muestras.R

class ProduccionFragment : Fragment() {

    private lateinit var shareViewModel: ProduccionViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
                ViewModelProviders.of(this).get(ProduccionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_produccion, container, false)
        val textView: TextView = root.findViewById(R.id.text_share)
        shareViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}