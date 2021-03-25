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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        changeStatusBarColor()

        //capturamos los editText
        val edt_nombre: EditText = findViewById(R.id.editTextNombre)
        val edt_email: EditText = findViewById(R.id.editTextEmail)
        val edt_telefono: EditText = findViewById(R.id.editTextTelefono)
        val edt_password: EditText = findViewById(R.id.editTextPassword)
        //capturamos el clip del boton
        val registrar: Button = findViewById(R.id.btnRegister)
        registrar.setOnClickListener {
            registrar.isEnabled = false
            registrar.text = "Cargando..."
            if (validarCampos(edt_nombre, edt_email, edt_telefono, edt_password)){
                //llamamos a la funcion que se encargara de guardar los datos
                registrarUsuario(edt_nombre, edt_email, edt_telefono, edt_password, registrar)
            }else{
                registrar.isEnabled = true
                registrar.text = "Registrarse"
            }
        }


    }

    private fun registrarUsuario(edtNombre: EditText, edtEmail: EditText, edtTelefono: EditText, edtPassword: EditText, btnRegistrar: Button) {
        //definimos la url se comunicara con la base de datos mysql
        val url = Url().direccion(Url.NombreUrl.REGISTRO)
        //declaramos una variable usando la libreria Volley
        val queue = Volley.newRequestQueue(this)
        //definimos la solicitud a realizar
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                //capturamos el JSON que recibimos
                val obj = JSONObject(it)
                //creamos variables con la informacion del JSON
                val json_success = obj.getBoolean("success")
                val json_message = obj.getString("message")
                //muestro el mensaje en un toast
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
                    edtEmail.setError(json_message)
                    btnRegistrar.isEnabled = true
                    btnRegistrar.text = "Registrarse"
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Error de conexion", Toast.LENGTH_LONG).show()
                btnRegistrar.isEnabled = true
                btnRegistrar.text = "Registrarse"
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("name", edtNombre.text.toString())
                params.put("email", edtEmail.text.toString())
                params.put("telefono", edtTelefono.text.toString())
                params.put("password", edtPassword.text.toString())
                return params
            }
        }
        //agregamos la solititud a la variable Valley para que se ejecute
        queue.add(stringRequest)
    }

    private fun validarCampos(edtNombre: EditText, edtEmail: EditText, edtTelefono: EditText, edtPassword: EditText): Boolean {
        var resultado = true
        val nombre = edtNombre.text.toString()
        val email = edtEmail.text.toString()
        val telefono = edtTelefono.text.toString()
        val password = edtPassword.text.toString()

        if (nombre.isEmpty()){
            edtNombre.setError("Ingrese un Nombre")
            resultado = false
        }else{
            if (nombre.length < 5){
                edtNombre.setError(getText(R.string.min_4_caracteres))
                resultado = false
            }
        }

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

        if (telefono.isEmpty()){
            edtTelefono.setError("Ingrese Telefono")
            resultado = false
        }else{
            val patterns = Patterns.PHONE
            if (!patterns.matcher(telefono).matches()){
                edtTelefono.setError("error telefono")
                resultado = false
            }
        }

        if (password.isEmpty()){
            edtPassword.setError("Ingrese un Password")
            resultado = false
        }else{
            if (password.length < 8){
                edtPassword.setError(getText(R.string.min_8_caracteres))
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