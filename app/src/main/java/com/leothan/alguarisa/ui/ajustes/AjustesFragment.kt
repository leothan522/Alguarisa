package com.leothan.alguarisa.ui.ajustes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.leothan.alguarisa.R
import com.leothan.alguarisa.config.SharedApp
import com.leothan.alguarisa.config.Url
import org.json.JSONObject

class AjustesFragment : Fragment() {

    private lateinit var viewModel: AjustesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AjustesViewModel::class.java)
        val root =  inflater.inflate(R.layout.fragment_ajustes, container, false)

        val til_name: TextInputLayout = root.findViewById(R.id.textInputName)
        val til_email: TextInputLayout = root.findViewById(R.id.textInputEmail)
        val til_telefono: TextInputLayout = root.findViewById(R.id.textInputTelefono)
        til_name.hint = SharedApp.prefs.name
        til_email.hint = SharedApp.prefs.email
        til_telefono.hint = SharedApp.prefs.telefono
        if (SharedApp.prefs.telefono == "null"){
            til_telefono.hint = "Telefono"
        }

        val edt_name: EditText = root.findViewById(R.id.editTextName)
        val edt_email: EditText = root.findViewById(R.id.editTextEmail)
        val edt_telefono: EditText = root.findViewById(R.id.editTextTelefono)
        val edt_password: EditText = root.findViewById(R.id.editTextPassword)
        val edt_nuevo: EditText = root.findViewById(R.id.editTextNuevoPassword)
        val btn_guardar: Button = root.findViewById(R.id.btnGuardar)

        btn_guardar.setOnClickListener {
            btn_guardar.isEnabled= false
            btn_guardar.text = "Cargando..."
            if (validarCampos(edt_name, edt_email, edt_telefono, edt_password, edt_nuevo)){
                guardarCambios(edt_name, edt_email, edt_telefono, edt_password, edt_nuevo, btn_guardar, til_name, til_email, til_telefono)
            }else{
                btn_guardar.isEnabled = true
                btn_guardar.text = "Guardar Cambios"
            }
        }


        return root
    }

    private fun guardarCambios(
        edtName: EditText,
        edtEmail: EditText,
        edtTelefono: EditText,
        edtPassword: EditText,
        edtNuevo: EditText,
        btnGuardar: Button,
        til_name: TextInputLayout,
        til_email: TextInputLayout,
        til_telefono: TextInputLayout
    ) {
        val url = Url().direccion(Url.NombreUrl.AJUSTES)
        val queue = Volley.newRequestQueue(activity)
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {

                val navView: NavigationView? = activity?.findViewById(R.id.nav_view)
                val header: View? = navView?.getHeaderView(0)
                val headerNombre: TextView? = header?.findViewById(R.id.headerName)
                val headerCorreo: TextView? = header?.findViewById(R.id.headerEmail)

                val obj = JSONObject(it)
                val json_success = obj.getBoolean("success")
                val json_message = obj.getString("message")
                Toast.makeText(activity, json_message, Toast.LENGTH_LONG).show()
                if (json_success){
                    val json_nombre = obj.getString("name")
                    val json_email = obj.getString("email")
                    val json_telefono = obj.getString("telefono")
                    if (json_nombre != "false"){
                        SharedApp.prefs.name = json_nombre
                        edtName.text = null
                        til_name.hint = SharedApp.prefs.name
                        headerNombre?.text = SharedApp.prefs.name
                    }
                    if (json_email != "false"){
                        SharedApp.prefs.email = json_email
                        edtEmail.text = null
                        til_email.hint = SharedApp.prefs.email
                        headerCorreo?.text = SharedApp.prefs.email
                    }
                    if (json_telefono != "false"){
                        SharedApp.prefs.telefono = json_telefono
                        edtTelefono.text = null
                        til_telefono.hint = SharedApp.prefs.telefono
                    }
                    if (json_nombre == "false" && json_email == "false" && json_telefono == "false"){
                        edtPassword.text = null
                        edtNuevo.text = null
                    }

                }else{
                    val json_error = obj.getString("error")
                    if (json_error == "email"){
                        edtEmail.setError(json_message)
                    }
                    if (json_error == "password"){
                        edtPassword.setError(json_message)
                    }
                }
                btnGuardar.isEnabled = true
                btnGuardar.text = "Guardar Cambios"
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Error de conexion.", Toast.LENGTH_LONG).show()
                btnGuardar.isEnabled = true
                btnGuardar.text = "Guardar Cambios"
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("name", edtName.text.toString())
                params.put("email", edtEmail.text.toString())
                params.put("telefono", edtTelefono.text.toString())
                params.put("password", edtPassword.text.toString())
                params.put("nuevo_password", edtNuevo.text.toString())
                params.put("id", SharedApp.prefs.id.toString())
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun validarCampos(
        edtName: EditText,
        edtEmail: EditText,
        edtTelefono: EditText,
        edtPassword: EditText,
        edtNuevo: EditText
    ): Boolean {
        var resultado = true
        val name = edtName.text.toString()
        val email = edtEmail.text.toString()
        val telefono = edtTelefono.text.toString()
        val password = edtPassword.text.toString()
        val nuevo = edtNuevo.text.toString()

        if (name.isEmpty() && email.isEmpty() && telefono.isEmpty() && password.isEmpty()){
            Toast.makeText(activity, "No se relizo ningun cambio.", Toast.LENGTH_LONG).show()
            resultado = false
        }

        if (name.isNotEmpty()){
            if (name.length < 4){
                edtName.setError(getText(R.string.min_4_caracteres))
                resultado = false
            }
        }

        if (email.isNotEmpty()){
            val patterns = Patterns.EMAIL_ADDRESS
            if (!patterns.matcher(email).matches()){
                edtEmail.setError(getText(R.string.error_email))
                resultado = false
            }
        }

        if (telefono.isNotEmpty()){
            val patterns = Patterns.PHONE
            if (!patterns.matcher(telefono).matches()){
                edtTelefono.setError("Ingrese Telefono")
                resultado = false
            }
        }

        if (password.isNotEmpty()){

            if (password.length < 8){
                edtPassword.setError(getText(R.string.min_8_caracteres))
                resultado = false
            }

            if (nuevo.isEmpty()){
                edtNuevo.setError("Ingrese un nuevo password")
                resultado = false
            }else{
                if (nuevo.length < 8){
                    edtNuevo.setError(getText(R.string.min_8_caracteres))
                    resultado = false
                }
                if (password == nuevo){
                    edtPassword.setError("Password Actual y Nuevo no pueden ser iguales ")
                    edtNuevo.setError("Password Actual y Nuevo  no pueden ser iguales ")
                    resultado = false
                }
            }

        }else{
            if (nuevo.isNotEmpty()){
                edtPassword.setError("Ingrese password actual")
                resultado = false
            }
        }


        return resultado
    }

}