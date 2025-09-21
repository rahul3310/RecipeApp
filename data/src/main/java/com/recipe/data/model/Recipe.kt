package com.recipe.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val pricePerServing : Int?,
    val isFavorite : Boolean,
    val instructions : String,
    val summary : String,
)