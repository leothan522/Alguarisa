package com.leothan.alguarisa

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leothan.alguarisa.config.Url
import org.json.JSONObject

class RecuperarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)
        changeStatusBarColor()
        
        val edt_email: EditText = findViewById(R.id.editTextEmail)
        val recuperar: Button = findViewById(R.id.btnRecuperar)
        recuperar.setOnClickListener {
            recuperar.isEnabled = false
            recuperar.text = "Cargando..."
            if (validarCampos(edt_email)){
                recuperarClave(edt_email, recuperar)
            }else{
                recuperar.isEnabled = true
                recuperar.text = "Enviar Correo"
            }
        }

        
    }

    private fun recuperarClave(edtEmail: EditText, recuperar: Button) {
        val url = Url().direccion(Url.NombreUrl.RECUPERAR)
        val queue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                val obj = JSONObject(it)
                val json_success = obj.getBoolean("success")
                val json_message = obj.getString("message")
                Toast.makeText(applicationContext, json_message, Toast.LENGTH_LONG).show()
                if (json_success){
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    edtEmail.setError(json_message)
                    recuperar.isEnabled = true
                    recuperar.text = "Enviar Correo"
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Error de conexion", Toast.LENGTH_LONG).show()
                recuperar.isEnabled = true
                recuperar.text = "Enviar Correo"
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("email", edtEmail.text.toString())
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun validarCampos(edtEmail: EditText): Boolean {
        var resultado = true
        val email = edtEmail.text.toString()
        if (email.isEmpty()){
            edtEmail.setError(getText(R.string.ingrese_email))
            resultado = false
        }else{
            val patterns = Patterns.EMAIL_ADDRESS
            if (!patterns.matcher(email).matches()){
                edtEmail.setError(getText(R.string.error_email))
                resultado = false
            }
        }
        return resultado
    }

    fun onLogin(View: View?){
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay)
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(resources.getColor(R.color.primaryColor))
        }
    }
}