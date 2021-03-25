package com.leothan.alguarisa.ui.cuenta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.SharedApp

class CuentaFragment : Fragment() {

    private lateinit var cuentaViewModel: CuentaViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        cuentaViewModel =
                ViewModelProvider(this).get(CuentaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cuenta, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val titulo: TextView = root.findViewById(R.id.titulo)
        titulo.text = SharedApp.prefs.name
        val subtitulo: TextView = root.findViewById(R.id.subTitulo)
        subtitulo.text = SharedApp.prefs.email
        val name: TextView = root.findViewById(R.id.name)
        name.text = SharedApp.prefs.name
        val telefono: TextView = root.findViewById(R.id.telefono)
        telefono.text = SharedApp.prefs.telefono
        val email: TextView = root.findViewById(R.id.email)
        email.text = SharedApp.prefs.email

        return root
    }
}