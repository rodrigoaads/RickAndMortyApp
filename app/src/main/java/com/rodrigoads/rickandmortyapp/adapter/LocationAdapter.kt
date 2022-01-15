package com.rodrigoads.rickandmortyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.rickandmortyapp.databinding.LocationItemListBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.location.Location

class LocationAdapter(
    val list: List<Location>,
    val getObjectInfos: getObjectInfos
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            LocationItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding, getObjectInfos)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class LocationViewHolder(
        private val locationItemListBinding: LocationItemListBinding,
        private val getObjectInfos: getObjectInfos
    ) : RecyclerView.ViewHolder(locationItemListBinding.root) {
        fun bind(location: Location) {
            locationItemListBinding.textViewLocationName.text = location.locationName
            locationItemListBinding.textViewLocationType.text = location.locationType

            locationItemListBinding.root.setOnClickListener {
                getObjectInfos.getLocation(location)
            }
        }
    }
}
