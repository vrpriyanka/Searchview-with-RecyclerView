package com.example.anmieapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.anmieapplication.data.repository.AnimeRepository
import com.example.anmieapplication.utils.Resource
import kotlinx.coroutines.Dispatchers


class AnimesViewModel : ViewModel() {

    private lateinit var animeRepository: AnimeRepository

    fun setRepository(animesRepository: AnimeRepository) {
        this.animeRepository = animesRepository
    }

    fun getListAnimes() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = animeRepository.getListAnimes("naruto").results))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}