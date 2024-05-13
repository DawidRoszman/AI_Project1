package eu.dawidroszman.foodlicious

import com.google.gson.annotations.SerializedName


data class FruitData (
    val id: String,
    @SerializedName("name")
    val fruitName: String,
    val description: String,
    val nutrients: String,
    val origin: String,
    val season: String,
    val type: String
)