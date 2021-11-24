package com.mattcapazz.wheelviana

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
  @SuppressLint("ClickableViewAccessibility")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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

  fun login(view: android.view.View) {
    startActivity(Intent(this, Login::class.java))
  }

  fun maps(view: android.view.View) {
    startActivity(Intent(this, Maps::class.java))
  }
}
