package com.careers.edvora.presentation.fragments.rides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.careers.edvora.R
import com.careers.edvora.databinding.SimpleRideItemBinding
import com.careers.edvora.domain.model.Ride
import com.careers.edvora.presentation.fragments.base.BaseAdapter
import com.careers.extensions.isDarkTheme
import com.careers.extensions.load
import javax.inject.Inject

class RideAdapter @Inject constructor(
) : BaseAdapter<Ride, SimpleRideItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleRideItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<Ride>() {
        override fun areItemsTheSame(oldItem: Ride, newItem: Ride) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ride, newItem: Ride) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: BaseViewHolder<SimpleRideItemBinding>, position: Int) {
        val ride = differ.currentList[position]
        holder.binding.apply {
            val context = root.context
            root.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (context.isDarkTheme()) R.color.black else R.color.white
                )
            )
            ivMap.load(ride.mapUrl)
            val id = String.format("%03d", ride.id)
            cpCityName.text = ride.city
            cpStateName.text = ride.state
            tvRideId.text = context.getString(R.string.ride_id_format, id)
            tvOriginStation.text =
                context.getString(R.string.origin_station_format, ride.originStationCode)
            tvStationPath.text =
                context.getString(R.string.station_path_format, ride.stationPath.toString())
            tvDate.text = context.getString(R.string.date_format, ride.date)
            tvDistance.text = context.getString(R.string.distance_format, ride.distance)
        }
    }
}