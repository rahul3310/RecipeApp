package com.recipe.network.modelDto

data class ExtendedIngredientDTO(
    val aisle: String?,
    val amount: Double?,
    val consistency: String?,
    val id: Int?,
    val image: String?,
    val measures: MeasuresDTO?,
    val meta: List<String>?,
    val name: String?,
    val nameClean: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?
)