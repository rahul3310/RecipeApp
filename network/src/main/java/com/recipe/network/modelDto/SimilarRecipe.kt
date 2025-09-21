package com.recipe.network.modelDto

data class SimilarRecipe(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String,
    val pricePerServing : Int?,
    val instructions : String,
    val summary : String,
)