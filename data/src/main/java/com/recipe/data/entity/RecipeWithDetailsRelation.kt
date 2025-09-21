package com.recipe.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithDetailsRelation(

    @Embedded
    val recipe: RecipeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val nutrients: List<NutrientEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val instructions: List<InstructionEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val equipment: List<EquipmentEntity>
)
