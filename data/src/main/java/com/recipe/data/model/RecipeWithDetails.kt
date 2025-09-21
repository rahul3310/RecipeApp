package com.recipe.data.model

data class RecipeWithDetails(
    val recipe: Recipe,
    val ingredients: List<Ingredient>,
    val nutrients: List<Nutrient>,
    val instructions: List<Instruction>,
    val equipment: List<Equipment>
)
