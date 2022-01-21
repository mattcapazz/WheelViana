package com.mattcapazz.wheelviana

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Settings : AppCompatActivity() {
  lateinit var toggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val nav = findViewById<NavigationView>(R.id.navView)

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
        R.id.schedule -> Toast.makeText(
          applicationContext,
          "Clicked Item 2",
          Toast.LENGTH_SHORT
        ).show()
        R.id.issues -> {
          val loginAct = Intent(this, ActivityReport::class.java).apply { }
          startActivity(loginAct)
        }
        R.id.login -> {
          val loginAct = Intent(this, Register::class.java).apply { }
          startActivity(loginAct)
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
}