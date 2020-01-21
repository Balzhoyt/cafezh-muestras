package com.balzhoyt.cafezh.muestras

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.balzhoyt.cafezh.muestras.utils.ImagenCircular
import com.balzhoyt.cafezh.muestras.utils.TranformacionImagenCircular
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        Credentials.getClient(this).disableAutoSignIn();

        /**
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Subir fotos a la nube", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        */

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = this.findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_capturarMuestra, R.id.nav_galeria, R.id.nav_chat,
                R.id.nav_configuracion, R.id.nav_produccion, R.id.nav_produccion), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Firebase UI
        val firebaseAuth =FirebaseAuth.getInstance()

        val hView = nav_view.getHeaderView(0)
        val txtUsuario = hView.findViewById(R.id.txtUsuario) as TextView
        val ivFotoPerfil = hView.findViewById(R.id.profile_image) as ImageView

        val user = firebaseAuth.currentUser
        user?.let {
            txtUsuario.text = user.displayName
            val photoUrl = user.photoUrl
            val imagen =ImagenCircular(applicationContext,ivFotoPerfil)
            Glide.with(applicationContext)
                    .load(photoUrl)
                    //.transform(TranformacionImagenCircular(applicationContext))
                    .into(ivFotoPerfil)
        }

        //Cerrar session firebase

        // when button is clicked, show the alert
        ivFotoPerfil.setOnClickListener {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Desea cerrar sesión?")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Si", DialogInterface.OnClickListener {
                       // dialog, id -> user!!.delete(); firebaseAuth.signOut();  finish()
                        dialog, id -> firebaseAuth.signOut();  finish()
                    })
                    // negative button text and action
                    .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                    })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Sesión de usuario")
            // show alert dialog
            alert.show()
        }

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
}
