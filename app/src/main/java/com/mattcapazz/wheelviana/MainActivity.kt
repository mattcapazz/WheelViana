package com.mattcapazz.wheelviana

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
  @SuppressLint("ClickableViewAccessibility")
  lateinit var toggle: ActionBarDrawerToggle
  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    loadData()
    val tag = "Login"
    auth = Firebase.auth

    val currentUser = auth.currentUser
    if (currentUser != null) {

      Log.d(tag, "arroz logado da main " + currentUser.email)
    }

    val db = Firebase.firestore

    db.collection("users")
      .get()
      .addOnSuccessListener { result ->
        for (document in result) {
          if (currentUser != null) {
            if (document.data.get("email") != currentUser!!.email) {
              Log.d(TAG, "${document.id} => ${document.data.get("email")}")
            } else {

              Log.d(TAG, "${document.id} => ${document.data.get("email")}")

              if (currentUser != null) {
                val emailTV = findViewById<TextView>(R.id.emailWelcome)
                emailTV.text = "Bem vindo, " + document.data.get("name")
              }
            }
          } else {
            Log.d(TAG, "${document.id} => ${document.data.get("email")}")
          }
        }
      }
      .addOnFailureListener { exception ->
        Log.w(TAG, "Error getting documents.", exception)
      }



    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val nav = findViewById<NavigationView>(R.id.navView)

    if (currentUser == null) {
      val navMenu: Menu = nav.menu
      navMenu.findItem(R.id.logout).isVisible = false
    }

    if (currentUser != null) {
      val navMenu: Menu = nav.menu
      navMenu.findItem(R.id.login).isVisible = false
    }
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
        R.id.schedule -> {
          val schedule = Intent(this, Schedule::class.java).apply { }
          startActivity(schedule)
        }
        R.id.issues -> {
          val loginAct = Intent(this, ActivityReport::class.java).apply { }
          startActivity(loginAct)
        }
        R.id.login -> {
          if (currentUser != null) {
            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.login).isVisible = false

          } else {
            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.logout).isVisible = true
            val loginAct = Intent(this, Register::class.java).apply { }
            startActivity(loginAct)
          }


        }
        R.id.logout -> {
          if (currentUser != null) {
            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.logout).isVisible = true
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
          } else {
            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.logout).isVisible = false
          }
        }
      }
      true
    }
  }

  private fun saveData() {
    val textoDe = findViewById<EditText>(R.id.editTde).text.toString()
    val textoPara = findViewById<EditText>(R.id.editTpara).text.toString()

    val deTV = findViewById<TextView>(R.id.deTv)
    deTV.text = textoDe

    val dePARA = findViewById<TextView>(R.id.paraTv)
    dePARA.text = textoPara

    val sharedPref = getSharedPreferences("usualRoute", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.apply {
      putString("STRING_KEY", textoDe)
      putString("STRING_KEY2", textoPara)
      //putBoolean("BOOLEAN_KEY", findViewById<Switch>(R.id.switch1).isChecked)
    }.apply()

    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
  }

  private fun loadData() {
    val sharedPref = getSharedPreferences("usualRoute", Context.MODE_PRIVATE)
    val savedString = sharedPref.getString("STRING_KEY", null)
    val savedString2 = sharedPref.getString("STRING_KEY2", null)
    sharedPref.getBoolean("BOOLEAN_KEY", false)

    val deTV = findViewById<TextView>(R.id.deTv)
    deTV.text = savedString
    val dePARA = findViewById<TextView>(R.id.paraTv)
    dePARA.text = savedString2
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (toggle.onOptionsItemSelected(item)) {
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  fun calendar(view: View) {
    val dateTv = findViewById<View>(R.id.dateTv) as TextView
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    var dpd =
      DatePickerDialog(this, { _, mYear, mMonth, mDay ->
        val mmMonth = mMonth + 1
        val date = "$mDay/$mmMonth/$mYear"
        dateTv.text = date
      }, year, month, day)
    dpd.show()
  }

  fun saveBtn(view: View) {
    saveData()
  }

  fun searchSch(view: View) {
    val edtt = findViewById<EditText>(R.id.editTde).text.toString()

    if (edtt.isNotBlank()) {
      val textoDe = findViewById<EditText>(R.id.editTde).text.toString()
      val textoPara = findViewById<EditText>(R.id.editTpara).text.toString()

      val intent = Intent(this@MainActivity, Schedule::class.java)
      intent.putExtra("De", textoDe)
      intent.putExtra("Para", textoPara)
      startActivity(intent)
      Log.e("Extra2", "estou no edittext")

    } else {
      val deTV = findViewById<TextView>(R.id.deTv).text.toString()
      val dePARA = findViewById<TextView>(R.id.paraTv).text.toString()

      val intent = Intent(this@MainActivity, Schedule::class.java)
      intent.putExtra("De", deTV)
      intent.putExtra("Para", dePARA)
      startActivity(intent)
      Log.e("Extra2", "Estou no textview")
    }
  }
}
