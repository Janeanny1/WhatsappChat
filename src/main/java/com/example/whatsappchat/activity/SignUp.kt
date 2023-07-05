package com.example.whatsappchat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappchat.MainActivity
import com.example.whatsappchat.R
import com.example.whatsappchat.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var name : EditText
    private lateinit var edtEmail : EditText
    private lateinit var password : EditText
    private lateinit var signup : Button

    private lateinit var auth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        edtEmail=findViewById(R.id.edtEmail)
        password =findViewById(R.id.password)
        name=findViewById(R.id.name)
        signup=findViewById(R.id.sign_up)

        signup.setOnClickListener {
            val name = name.text.toString()
            val email = edtEmail.text.toString()
            val password = password.text.toString()

            signup(name,email,password)
        }
    }

    private fun signup(name : String, email: String, password: String) {
        //creating new user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, signed-in user's information
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Failed to sign up", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(Users(name,email,uid))

    }
}