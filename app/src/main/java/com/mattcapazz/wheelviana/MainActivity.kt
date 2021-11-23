package com.mattcapazz.wheelviana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun login(view: android.view.View) {
    startActivity(Intent(this, Login::class.java))
  }

  fun maps(view: android.view.View) {
    startActivity(Intent(this, Maps::class.java))
  }
}
