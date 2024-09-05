package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_vehicle_entry.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var vehicleAdapter: VehicleAdapter
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupRecyclerView()

        // Handle Search Button Click
        binding.searchButton.setOnClickListener {
            val vehicleNumber = binding.searchEditText.text.toString().trim()
            if (TextUtils.isEmpty(vehicleNumber)) {
                Toast.makeText(this, "Please enter a vehicle number", Toast.LENGTH_SHORT).show()
            } else {
                searchVehicle(vehicleNumber)
            }
        }

        // Handle Fetch All Button Click
        binding.fetchAllButton.setOnClickListener {
            // Start VehicleListActivity to display all vehicles
            val intent = Intent(this, VehicleListActivity::class.java)
            startActivity(intent)
        }

        // Handle Logout Icon Click
        val logoutIcon: ImageView = findViewById(R.id.iconLogout)
        logoutIcon.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Close the dashboard activity
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        vehicleAdapter = VehicleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = vehicleAdapter
    }

    private fun searchVehicle(vehicleNumber: String) {
        db.collection("allowedVehicles")
            .get()
            .addOnSuccessListener { result ->
                val filteredVehicles = result.map { document ->
                    document.toObject(Vehicle::class.java)
                }.filter { vehicle ->
                    vehicle.veh_num.contains(vehicleNumber, ignoreCase = true)
                }
                vehicleAdapter.submitList(filteredVehicles)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error searching vehicle: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleResult(result: QuerySnapshot) {
        if (result.isEmpty) {
            Toast.makeText(this, "No vehicles found", Toast.LENGTH_SHORT).show()
            vehicleAdapter.submitList(emptyList()) // Clear the adapter's list if no results
        } else {
            val vehicles = result.map { document ->
                document.toObject(Vehicle::class.java)
            }
            vehicleAdapter.submitList(vehicles) // Update RecyclerView with search results
        }
    }
}
