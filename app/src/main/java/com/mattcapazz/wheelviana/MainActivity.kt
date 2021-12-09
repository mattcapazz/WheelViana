package com.mattcapazz.wheelviana

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*
import android.widget.TextView




class MainActivity : AppCompatActivity() {
  @SuppressLint("ClickableViewAccessibility")
  lateinit var toggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)




    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val nav = findViewById<NavigationView>(R.id.navView)


    toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    nav.setNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.miItem1 -> {
          val intent = Intent(this, Maps::class.java).apply {
          }
          startActivity(intent)
        }

        R.id.miItem2 -> Toast.makeText(
          applicationContext,
          "Clicked Item 2",
          Toast.LENGTH_SHORT
        ).show()

        R.id.miItem3 -> Toast.makeText(
          applicationContext,
          "Clicked Item 3",
          Toast.LENGTH_SHORT
        ).show()
      }
      true
    }

    val eT: EditText = findViewById(R.id.editText)
    eT.setOnTouchListener(OnTouchListener { _, event ->
      // Left -> 0, Top -> 1, Right -> 2, Bottom -> 3
      if (event.action == MotionEvent.ACTION_UP) {
        if (event.rawX >= eT.right - eT.compoundDrawables[2].bounds.width()
        ) {
          Toast.makeText(this, "Teste", Toast.LENGTH_LONG).show()
          return@OnTouchListener true
        }
      }
      false
    })
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


}
