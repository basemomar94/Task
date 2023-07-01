package com.example.task.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.R
import com.example.task.databinding.ItemFacilityBinding
import com.example.task.models.Facility

class FacilityAdapter(val facilities: List<Facility>, val context: Context) :
    RecyclerView.Adapter<FacilityAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemFacilityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = facilities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facility = facilities[position]
        holder.binding.textView.text = facility.name
        Glide.with(context).load(R.drawable.boat)
    }
}