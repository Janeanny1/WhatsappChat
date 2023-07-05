package com.example.whatsappchat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappchat.MainActivity
import com.example.whatsappchat.R
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail : EditText
    private lateinit var password : EditText
    private lateinit var login : Button
    private lateinit var signup : Button

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        edtEmail= findViewById(R.id.edtEmail)
        password =findViewById(R.id.password)
        login=findViewById(R.id.login)
        signup=findViewById(R.id.sign_up)

        signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = password.text.toString()
            
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        //user login
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // user login
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn, "email or password does not exit", Toast.LENGTH_SHORT).show()
                }
            }
    }
}