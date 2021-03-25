package com.leothan.alguarisa.ui.usuarios

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leothan.alguarisa.MainActivity
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.Url

class UsuariosFragment : Fragment() {

    private lateinit var usuariosViewModel: UsuariosViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        usuariosViewModel =
                ViewModelProvider(this).get(UsuariosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_usuarios, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val url = Url().direccion(Url.NombreUrl.USUARIOS)
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