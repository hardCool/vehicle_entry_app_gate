package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Check if the user is already logged in
        if (auth.currentUser != null) {
            // User is already logged in, navigate to DashboardActivity
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            // User is not logged in, navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
///
