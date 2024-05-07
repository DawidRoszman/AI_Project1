package eu.dawidroszman.foodlicious

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object CameraScreen : Screen("camera_screen")
}