package org.d3if3128.asesmen3mobpro.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3128.asesmen3mobpro.model.Laptop
import org.d3if3128.asesmen3mobpro.network.ApiStatus
import org.d3if3128.asesmen3mobpro.network.LaptopApi
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Laptop>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO){
            status.value = ApiStatus.LOADING
            try {
               data.value = LaptopApi.service.getLaptop(userId)
                status.value = ApiStatus.SUCCESS
            }
            catch (e: Exception){
                Log.d("MainViewModel", "Failure ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, processor: String, bitmap: Bitmap){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val result = LaptopApi.service.postLaptop(
                    userId,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    processor.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception){
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteData(userId: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = LaptopApi.service.deleteLaptop(userId, id)
                if (response.status == "success") {
                    // Jika berhasil, hapus item dari daftar lokal
                    retrieveData(userId)
                } else {
                    throw Exception(response.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part{
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG,80,stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0,byteArray.size)
        return MultipartBody.Part.createFormData(
            "image","image.jpg", requestBody)
    }

    fun clearMessage(){errorMessage.value = null}

}