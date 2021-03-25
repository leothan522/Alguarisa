package com.leothan.alguarisa.ui.cerrar

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.alguarisa.LoginActivity
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.SharedApp

class CerrarFragment : Fragment() {

    private lateinit var viewModel: CerrarViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(CerrarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cerrar, container, false)

        SharedApp.prefs.remover()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()

        return root
    }

}