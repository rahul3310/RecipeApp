package com.recipe.data.mapper

import com.recipe.data.entity.EquipmentEntity
import com.recipe.data.entity.IngredientEntity
import com.recipe.data.entity.InstructionEntity
import com.recipe.data.entity.NutrientEntity
import com.recipe.data.entity.RecipeEntity
import com.recipe.data.entity.RecipeWithDetailsRelation
import com.recipe.data.model.RecipeWithDetails
import com.recipe.network.modelDto.RecipeDTO

fun RecipeDTO.toEntities(): RecipeWithDetailsRelation {
    val recipeEntity = RecipeEntity(
        id = id,
        title = title,
        servings = servings,
        pricePerServing = pricePerServing,
        instructions = instructions,
        image = image,
        readyInMinutes = readyInMinutes,
        summary = summary
    )

    val ingredients = extendedIngredients?.map { ing ->
        IngredientEntity(
            recipeId = id,
            name = ing.name,
            amount = ing.amount,
            unit = ing.unit,
            original = ing.original,
            image = ing.image
        )
    }

    val instructionsList = analyzedInstructions
        ?.flatMap { it.steps ?: emptyList() }
        ?.map { step ->
            InstructionEntity(
                recipeId = id,
                stepNumber = step.number ?: 1,
                stepText = step.step ?: ""
            )
        }

    val equipmentList = analyzedInstructions?.flatMap { it.steps ?: emptyList() }
        ?.flatMap { it.equipment ?: emptyList() }
        ?.map { eq ->
            EquipmentEntity(
                recipeId = id,
                name = eq.name ?: "",
                image = eq.image
            )
        }


    val nutrients = emptyList<NutrientEntity>()

    return RecipeWithDetailsRelation(
        recipe = recipeEntity,
        ingredients = ingredients?:emptyList(),
        nutrients = nutrients,
        instructions = instructionsList?:emptyList(),
        equipment = equipmentList?:emptyList()
    )
}
