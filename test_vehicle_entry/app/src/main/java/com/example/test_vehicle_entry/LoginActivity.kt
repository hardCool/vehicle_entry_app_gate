package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupLink = findViewById<TextView>(R.id.signupLink)
        val loginEmail = findViewById<EditText>(R.id.loginEmail)
        val loginPassword = findViewById<EditText>(R.id.loginPassword)

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkUserApproval()  // Check if the user is approved
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun checkUserApproval() {
        // Check user approval status (this could be handled via Firestore or another backend service)
        // Assuming approval status is handled on Firebase
        val user = auth.currentUser
        // Simulate the approval check
        if (user != null) {
            // If the user is approved, navigate to DashboardActivity
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            // Show a message that approval is pending
            Toast.makeText(this, "Approval is pending. Please wait for approval.", Toast.LENGTH_SHORT).show()
        }
    }
}
