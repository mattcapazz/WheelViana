package com.mattcapazz.wheelviana

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityReport : AppCompatActivity() {
  lateinit var toggle: ActionBarDrawerToggle

  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_report)

  val TAG = "Extra"

    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val nav = findViewById<NavigationView>(R.id.navView)
    val navMenu: Menu = nav.menu
    navMenu.findItem(R.id.issues).isVisible = false
    navMenu.findItem(R.id.login).isVisible = false

    toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    nav.setNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.gMap -> {
          val gmapAct = Intent(this, Maps::class.java).apply {
          }
          startActivity(gmapAct)
        }
        R.id.dashboard -> {
          startActivity(Intent(this, MainActivity::class.java))
        }
      }
      true
    }
  }




  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (toggle.onOptionsItemSelected(item)) {
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  fun btnInc(view: android.view.View) {
    val db = Firebase.firestore


    val incidencia = findViewById<EditText>(R.id.etIncidencia).text.toString()
    val incid = hashMapOf(
      "name" to incidencia

    )

    db.collection("Incidencias")
      .add(incid)
      .addOnSuccessListener { documentReference ->
        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
      }
      .addOnFailureListener { e ->
        Log.w(TAG, "Error adding document", e)
      }
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)

  }
}