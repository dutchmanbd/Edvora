package com.careers.edvora.presentation.fragments.rides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.careers.edvora.databinding.SimpleStateCityItemBinding
import com.careers.edvora.presentation.fragments.base.BaseAdapter
import javax.inject.Inject

class CityAdapter @Inject constructor(
) : BaseAdapter<String, SimpleStateCityItemBinding>() {
    override fun initializeViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = SimpleStateCityItemBinding.inflate(layoutInflater, parent, false)

    override fun initializeDiffItemCallback() = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<SimpleStateCityItemBinding>,
        position: Int
    ) {
        val state = differ.currentList[position]
        holder.binding.tvName.text = state
        holder.binding.root.setOnClickListener { view ->
            listener?.let { click ->
                click(view, state)
            }
        }
    }
}