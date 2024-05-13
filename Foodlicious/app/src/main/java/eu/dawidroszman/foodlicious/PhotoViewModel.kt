package eu.dawidroszman.foodlicious

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PhotoViewModel : ViewModel() {
    var photoUiState: FruitData by mutableStateOf(FruitData("", "", "", "", "", "", ""))
    private set

    fun getFruitData(imagePath: String){
        viewModelScope.launch {
            val file = File(imagePath)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val result = PhotoApi.retrofitService.getFruitData(body)
            photoUiState = result
        }
    }

}