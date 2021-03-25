package com.leothan.alguarisa

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leothan.alguarisa.config.SharedApp

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_usuarios,
                R.id.nav_cerrar,
                R.id.nav_ajustes,
                R.id.nav_buscar,
                R.id.nav_familias,
                R.id.nav_modulo_clap,
                R.id.nav_ferias_campo,
                R.id.nav_plan_proteico,
                R.id.nav_tienda_fisica,
                R.id.nav_tienda_enlinea,
                R.id.nav_tienda_movil
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Bottom Navegation View
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        //Header
        val header: View = navView.getHeaderView(0)
        //val imagen: ImageView = header.findViewById(R.id.headerImage)
        val nombre: TextView = header.findViewById(R.id.headerName)
        val correo: TextView = header.findViewById(R.id.headerEmail)
        nombre.text = SharedApp.prefs.name
        correo.text = SharedApp.prefs.email

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //action menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    //ENLACES PARA LOS BOTONES DEL HOME HOME

    fun irModuloClap(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_modulo_clap)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irFeriasCampo(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_ferias_campo)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irPlanProteico(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_plan_proteico)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irTiendaFisica(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_tienda_fisica)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irTiendaEnlinea(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_tienda_enlinea)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irTiendaMovil(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_tienda_movil)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

    fun irUsuarios(View: View)
    {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_usuarios)
            .setArguments(null)
            .createPendingIntent()
        pendingIntent.send()
    }

}