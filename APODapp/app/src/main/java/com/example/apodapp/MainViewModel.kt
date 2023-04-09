package com.example.apodapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val context: Context) : ViewModel() {
    private val apodRepository = ApodRepository(context)

    private val _apodData = MutableLiveData<ApodEntity>()
    val apodData: LiveData<ApodEntity> get() = _apodData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        fetchApodData()
    }

    fun fetchApodData(date: String? = null) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val apodEntity = withContext(Dispatchers.IO) {
                    apodRepository.getApod(date)
                }
                _apodData.value = apodEntity
            } catch (exception: Exception) {
                _errorMessage.value = "Error fetching data: ${exception.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
