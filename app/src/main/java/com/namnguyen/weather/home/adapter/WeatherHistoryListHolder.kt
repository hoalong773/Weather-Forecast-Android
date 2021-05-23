package com.namnguyen.weather.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.namnguyen.weather.databinding.ItemHistoryWeatherBinding

class WeatherHistoryListHolder(private val binding: ItemHistoryWeatherBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: String) {
        binding.apply {
            cityName = data
            executePendingBindings()
        }
    }
}