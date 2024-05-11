package eu.dawidroszman.foodlicious

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {
    var photoUiState: String by mutableStateOf("")
    private set

    init{
        getHello()
    }

    fun getHello(){
        viewModelScope.launch {
            val result = PhotoApi.retrofitService.getHello()
            photoUiState = result
        }
    }

}