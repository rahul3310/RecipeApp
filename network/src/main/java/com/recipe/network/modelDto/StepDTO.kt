package com.recipe.network.modelDto

data class StepDTO(
    val equipment: List<EquipmentDTO>?,
    val ingredientDTOS: List<IngredientDTO>?,
    val length: LengthDTO?,
    val number: Int?,
    val step: String?
)