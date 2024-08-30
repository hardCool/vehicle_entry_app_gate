package com.example.test_vehicle_entry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_vehicle_entry.databinding.ActivityVehicleListBinding
import com.google.firebase.firestore.FirebaseFirestore

class VehicleListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVehicleListBinding
    private val db = FirebaseFirestore.getInstance()
    private val vehicleAdapter = VehicleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchAllowedVehicles()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = vehicleAdapter
    }

    private fun fetchAllowedVehicles() {
        db.collection("allowedVehicles")
            .get()
            .addOnSuccessListener { result ->
                val vehicles = result.map { document ->
                    document.toObject(Vehicle::class.java)
                }
                vehicleAdapter.submitList(vehicles)
            }
            .addOnFailureListener { exception ->
                // Handle error appropriately
                // Optionally, show a Toast or Log the error
            }
    }
}
