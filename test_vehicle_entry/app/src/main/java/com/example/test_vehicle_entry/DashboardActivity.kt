package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_vehicle_entry.databinding.ActivityDashboardBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var vehicleAdapter: VehicleAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.searchButton.setOnClickListener {
            val vehicleNumber = binding.searchEditText.text.toString().trim()
            if (TextUtils.isEmpty(vehicleNumber)) {
                Toast.makeText(this, "Please enter a vehicle number", Toast.LENGTH_SHORT).show()
            } else {
                searchVehicle(vehicleNumber)
            }
        }

        binding.fetchAllButton.setOnClickListener {
            // Start VehicleListActivity to display all vehicles
            val intent = Intent(this, VehicleListActivity::class.java)
            startActivity(intent)
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
