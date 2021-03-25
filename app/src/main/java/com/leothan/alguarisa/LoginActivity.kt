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
import com.leothan.alguarisa.config.SharedApp
import com.leothan.alguarisa.config.Url
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edt_email: EditText = findViewById(R.id.editTextEmail)
        val edt_password: EditText = findViewById(R.id.editTextPassword)
        val login: Button = findViewById(R.id.btnLogin)
        login.setOnClickListener {
            login.isEnabled = false
            login.text = "Cargando..."
            if (validarCampos(edt_email, edt_password, login)){
                iniciarSession(edt_email, edt_password, login)
            }else{
                login.isEnabled = true
                login.text = getText(R.string.Login)
            }
        }

    }

    private fun iniciarSession(edtEmail: EditText, edtPassword: EditText, login: Button) {
        val url = Url().direccion(Url.NombreUrl.LOGIN)
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
                        SharedApp.prefs.login = true
                        SharedApp.prefs.id = obj.getInt("id")
                        SharedApp.prefs.name = obj.getString("name")
                        SharedApp.prefs.email = obj.getString("email")
                        SharedApp.prefs.telefono = obj.getString("telefono")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        login.isEnabled = true
                        login.text = getText(R.string.Login)
                        if (obj.getString("error") == "email"){
                            edtEmail.setError("Email no registrado")
                        }else{
                            edtPassword.setError("Password Incorrecto")
                        }
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, "Error de conexion", Toast.LENGTH_LONG).show()
                    login.isEnabled = true
                    login.text = getText(R.string.Login)
                }
        ) {
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params.put("email", edtEmail.text.toString())
                params.put("password", edtPassword.text.toString())
                return params
            }

        }

        queue.add(stringRequest)
    }

    private fun validarCampos(edtEmail: EditText, edtPassword: EditText, login: Button): Boolean {
        var resultado = true
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

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

        if (password.isEmpty()){
            edtPassword.setError("Ingrese Password")
            resultado = false
        }else{
            if (password.length < 8){
                edtPassword.setError(getText(R.string.min_8_caracteres))
                resultado = false
            }
        }

        return resultado
    }

    fun onRegister(View: View?){
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
    }

    fun onRecuperar(View: View?){
        startActivity(Intent(this, RecuperarActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
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