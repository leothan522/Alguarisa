package com.leothan.alguarisa.ui.ferias_campo

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leothan.alguarisa.MainActivity
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.SharedApp
import com.leothan.alguarisa.config.Url

class FeriasCampoFragment : Fragment() {

    private lateinit var viewModel: FeriasCampoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FeriasCampoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ferias_campo, container, false)

        val url = Url().direccion(Url.NombreUrl.FERIAS_CAMPO) + "/" + SharedApp.prefs.id
        val webView: WebView = root.findViewById(R.id.webView)
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            loadWeb(webView, url, swipeRefreshLayout)
        }
        loadWeb(webView, url, swipeRefreshLayout)

        return root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWeb(webView: WebView, url: String, swipeRefreshLayout: SwipeRefreshLayout) {
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        swipeRefreshLayout.isRefreshing = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            //
        }
        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> //This is the filter
            if (event.action !== KeyEvent.ACTION_DOWN) return@OnKeyListener true
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    (activity as MainActivity?)!!.onBackPressed()
                }
                return@OnKeyListener true
            }
            false
        })
    }

}