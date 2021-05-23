package com.namnguyen.weather.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.namnguyen.weather.databinding.ItemHistoryWeatherBinding

class WeatherHistoryListAdapter : ListAdapter<String, WeatherHistoryListHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryListHolder {
        val binding =
            ItemHistoryWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherHistoryListHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHistoryListHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
