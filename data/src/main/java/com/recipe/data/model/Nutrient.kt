package com.recipe.data.model

data class Nutrient(
    val name: String,
    val amountWithUnit: String,
    val percentOfDailyNeeds: Double?
)