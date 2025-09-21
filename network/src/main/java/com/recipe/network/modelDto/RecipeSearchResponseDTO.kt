package com.recipe.network.modelDto

data class RecipeSearchResponseDTO(
    val offset: Int,
    val number: Int,
    val results: List<RecipeSearchedDTO>,
    val totalResults: Int
)

data class RecipeSearchedDTO(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String
)
