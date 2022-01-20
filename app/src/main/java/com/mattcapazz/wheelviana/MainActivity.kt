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

    val TAG = "Login"

    auth = Firebase.auth



    val currentUser = auth.currentUser



    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val nav = findViewById<NavigationView>(R.id.navView)

    if (currentUser == null) {

      val navMenu: Menu = nav.menu
      navMenu.findItem(R.id.logout).isVisible = false}





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
          val schedule = Intent(this, Schedule::class.java).apply {  }
          startActivity(schedule)
        }

        R.id.issues -> {
          val loginAct = Intent(this, ActivityReport::class.java).apply {  }
          startActivity(loginAct)
        }

        R.id.login -> {
          val loginAct = Intent(this, Register::class.java).apply {  }
          startActivity(loginAct)
        }
        R.id.logout -> {
          if (currentUser != null) {

            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.logout).isVisible = true
            FirebaseAuth.getInstance().signOut();
          } else {
            val navMenu: Menu = nav.menu
            navMenu.findItem(R.id.logout).isVisible = false

          }

        }

      }
      true
    }


  }



  fun saveData() {
    val textoDe = "De:" + "  " + findViewById<EditText>(R.id.editTde).text.toString()

    val textoPara = "Para:" + "  " + findViewById<EditText>(R.id.editTpara).text.toString()


    val deTV = findViewById<TextView>(R.id.deTv) as TextView
    deTV.setText(textoDe)
    val dePARA = findViewById<TextView>(R.id.paraTv) as TextView
    dePARA.setText(textoPara)

    val sharedPref = getSharedPreferences("usualRoute", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.apply{
      putString("STRING_KEY", textoDe)
      putString("STRING_KEY2", textoPara)
      putBoolean("BOOLEAN_KEY", findViewById<Switch>(R.id.switch1).isChecked)
    }.apply()

    Toast.makeText(this,"Data Saved", Toast.LENGTH_SHORT).show()
  }

  fun loadData() {
    val sharedPref = getSharedPreferences("usualRoute", Context.MODE_PRIVATE)
    val savedString = sharedPref.getString("STRING_KEY", null)
    val savedString2 = sharedPref.getString("STRING_KEY2", null)
    val savedSwitch = sharedPref.getBoolean("BOOLEAN_KEY", false)


    val deTV = findViewById<TextView>(R.id.deTv) as TextView
    deTV.setText(savedString)
    val dePARA = findViewById<TextView>(R.id.paraTv) as TextView
    dePARA.setText(savedString2)
    findViewById<Switch>(R.id.switch1).isChecked = savedSwitch


  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (toggle.onOptionsItemSelected(item)) {
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  fun login(view: android.view.View) {
    startActivity(Intent(this, Login::class.java))
  }

  fun maps(view: android.view.View) {
    startActivity(Intent(this, Maps::class.java))
  }

  fun calendar(view: android.view.View) {
    val dateTv = findViewById<View>(R.id.dateTv) as TextView
    val c= Calendar.getInstance()
    val year= c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    var dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear,mMonth , mDay ->
      val mmMonth = mMonth+1
      val date = "$mDay/$mmMonth/$mYear"
      dateTv.setText(date)
    },year,month,day)
    dpd.show()
  }

  fun saveBtn(view: android.view.View) {
    saveData()
  }

  fun SearchSch(view: android.view.View) {
    val edtt = findViewById<EditText>(R.id.editTde).text.toString()

    if(edtt.isNullOrBlank() == false){
      val textoDe = findViewById<EditText>(R.id.editTde).text.toString()
      val textoPara = findViewById<EditText>(R.id.editTpara).text.toString()

      val intent = Intent(this@MainActivity,Schedule::class.java)
      intent.putExtra("De",textoDe)
      intent.putExtra("Para",textoPara)
      startActivity(intent)
      Log.e("Extra2", "estou no edittext")

    } else {
      val deTV = findViewById<TextView>(R.id.deTv).text.toString()
      val dePARA = findViewById<TextView>(R.id.paraTv).text.toString()

      val intent = Intent(this@MainActivity,Schedule::class.java)
      intent.putExtra("De",deTV)
      intent.putExtra("Para",dePARA)
      startActivity(intent)
      Log.e("Extra2", "estou no textview")


    }
  }
}
