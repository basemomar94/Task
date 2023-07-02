package com.example.task.ui.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.R
import com.example.task.databinding.ItemFacilityBinding
import com.example.task.models.Option
import com.example.task.util.DataConstants

class OptionsAdapter(
    var options: List<Option>,
    val context: Context,
    val optionsInterface: OptionsInterface,
    val facilityId: Int
) :
    RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemFacilityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = options.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.binding.textView.text = option.name
        Glide.with(context).load(
            when (option.icon) {
                DataConstants.APARTMENT -> R.drawable.apartment
                DataConstants.CONDO -> R.drawable.condo
                DataConstants.BOAT -> R.drawable.boat
                DataConstants.LAND -> R.drawable.land
                DataConstants.NO_ROOMS -> R.drawable.no_room
                DataConstants.ROOMS -> R.drawable.rooms
                DataConstants.SWIMMING -> R.drawable.swimming
                DataConstants.GARAGE -> R.drawable.garage
                DataConstants.GARDEN -> R.drawable.garden
                else -> R.drawable.boat
            }
        ).into(holder.binding.imageView)
        if (option.isSelected) {
            holder.binding.imageView.setColorFilter(R.color.purple_200, PorterDuff.Mode.DARKEN)
        } else {
            holder.binding.imageView.clearColorFilter()
        }
        if (option.isEnabled) {
            holder.binding.root.isEnabled = true
          //  holder.binding.imageView.clearColorFilter()
        } else {
            holder.binding.root.isEnabled = false
          //  holder.binding.imageView.setColorFilter(androidx.appcompat.R.color.material_blue_grey_800, PorterDuff.Mode.DARKEN)
        }
        holder.binding.root.setOnClickListener {
            when (facilityId) {
                1 -> optionsInterface.selectingTypeItem(
                    option,
                    position,
                    options,
                    facilityId
                )

                2 -> optionsInterface.selectingRoomItem(
                    option,
                    position,
                    options,
                    facilityId
                )
                3 -> optionsInterface.selectingOtherItem(
                    option,
                    position,
                    options,
                    facilityId
                )

            }
//            option.isSelected = !option.isSelected
//            notifyItemChanged(position)
        }
    }

    fun updateList(newList: List<Option>?) {
        if (newList != null) {
            options = newList
            notifyDataSetChanged()
        }
    }

    interface OptionsInterface {
        fun selectingTypeItem(option: Option, position: Int, list: List<Option>, facilityId: Int)
        fun selectingRoomItem(option: Option, position: Int, list: List<Option>, facilityId: Int)

        fun selectingOtherItem(option: Option, position: Int, list: List<Option>, facilityId: Int)


    }
}