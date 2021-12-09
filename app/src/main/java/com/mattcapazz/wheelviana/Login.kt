package com.mattcapazz.wheelviana

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth
  val TAG = "Login"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    auth = Firebase.auth
  }

  public override fun onStart() {
    super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
    val currentUser = auth.currentUser
    if (currentUser != null) {
      Log.d(TAG, "arroz")
    }
  }

  fun createAccount(view: android.view.View) {
    val email = findViewById<EditText>(R.id.email).text.toString()
    val password = findViewById<EditText>(R.id.password).text.toString()

    auth.createUserWithEmailAndPassword(email, password)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          Log.d(TAG, "createUserWithEmail:success")
          val user = auth.currentUser
          //updateUI(user)
        } else {
          // If sign in fails, display a message to the user.
          Log.w(TAG, "createUserWithEmail:failure", task.exception)
          Toast.makeText(
            baseContext, "Authentication failed.",
            Toast.LENGTH_SHORT
          ).show()
          //updateUI(null)
        }
      }
  }

  fun loginAccount(view: android.view.View) {
    val email = findViewById<EditText>(R.id.email).text.toString()
    val password = findViewById<EditText>(R.id.password).text.toString()


    auth.signInWithEmailAndPassword(email, password)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          Log.d(TAG, "signInWithEmail:success")
          val user = auth.currentUser
          //updateUI(user)
        } else {
          // If sign in fails, display a message to the user.
          Log.w(TAG, "signInWithEmail:failure", task.exception)
          Toast.makeText(
            baseContext, "Authentication failed.",
            Toast.LENGTH_SHORT
          ).show()
          // updateUI(null)
        }
      }
  }


}
