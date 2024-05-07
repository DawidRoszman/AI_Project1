package eu.dawidroszman.foodlicious

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(){
    val scaffoldState =  rememberBottomSheetScaffoldState()
    BottomSheetScaffold(scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {}) {
        padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)){
    }
        
    }
}