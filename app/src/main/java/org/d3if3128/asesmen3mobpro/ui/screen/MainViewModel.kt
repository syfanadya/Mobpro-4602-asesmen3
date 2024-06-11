package org.d3if3128.asesmen3mobpro.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3if3128.asesmen3mobpro.model.Laptop
import org.d3if3128.asesmen3mobpro.network.ApiStatus
import org.d3if3128.asesmen3mobpro.network.LaptopApi

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Laptop>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO){
            status.value = ApiStatus.LOADING
            try {
               data.value = LaptopApi.service.getLaptop()
                status.value = ApiStatus.SUCCESS
            }
            catch (e: Exception){
                Log.d("MainViewModel", "Failure ${e.message}")

            }
        }
    }

}