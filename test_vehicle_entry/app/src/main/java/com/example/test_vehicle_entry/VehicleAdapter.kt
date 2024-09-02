package com.example.test_vehicle_entry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test_vehicle_entry.databinding.ItemVehicleBinding

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private val vehicles = mutableListOf<Vehicle>()

    fun submitList(list: List<Vehicle>) {
        vehicles.clear()
        vehicles.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount(): Int = vehicles.size

    class VehicleViewHolder(private val binding: ItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: Vehicle) {
            binding.vehicle = vehicle
            binding.executePendingBindings()
        }
    }
}
