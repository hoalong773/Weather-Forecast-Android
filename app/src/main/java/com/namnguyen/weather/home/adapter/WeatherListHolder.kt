package com.namnguyen.weather.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.namnguyen.weather.databinding.ItemWeatherBinding
import com.namnguyen.weather.model.WeatherInfo

class WeatherListHolder(private val binding: ItemWeatherBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: WeatherInfo) {
        binding.apply {
            weatherInfo = data
            executePendingBindings()
        }
    }
}