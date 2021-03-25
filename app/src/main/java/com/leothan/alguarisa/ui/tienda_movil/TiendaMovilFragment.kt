package com.leothan.alguarisa.ui.tienda_movil

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leothan.alguarisa.MainActivity
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.SharedApp
import com.leothan.alguarisa.config.Url


class TiendaMovilFragment : Fragment() {

    private lateinit var viewModel: TiendaMovilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TiendaMovilViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tienda_movil, container, false)

        val url = Url().direccion(Url.NombreUrl.TIENDA_MOVIL) + "/" + SharedApp.prefs.id
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