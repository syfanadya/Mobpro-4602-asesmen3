package org.d3if3128.asesmen3mobpro.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3128.asesmen3mobpro.network.LaptopApi

class MainViewModel : ViewModel() {

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val result = LaptopApi.service.getLaptop()
                Log.d("MainViewModel", "Success: $result")
            }
            catch (e: Exception){
                Log.d("MainViewModel", "Failure ${e.message}")

            }
        }
    }

}