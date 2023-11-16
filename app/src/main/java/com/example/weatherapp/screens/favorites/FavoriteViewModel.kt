package com.example.weatherapp.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getFavorites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isEmpty()) {
                    Log.d("FAVS", ": EMPTY_FAVS")
                } else {
                    _favList.value = listOfFavs
                    Log.d("FAVS", ":${favList.value}")
                }

            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.insertFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.updateFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.deleteFavorite(favorite)
    }
}