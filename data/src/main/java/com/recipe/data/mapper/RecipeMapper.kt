package com.recipe.data.mapper

import com.recipe.data.entity.EquipmentEntity
import com.recipe.data.entity.IngredientEntity
import com.recipe.data.entity.InstructionEntity
import com.recipe.data.entity.NutrientEntity
import com.recipe.data.entity.RecipeEntity
import com.recipe.data.entity.RecipeWithDetailsRelation
import com.recipe.data.model.Equipment
import com.recipe.data.model.Ingredient
import com.recipe.data.model.Instruction
import com.recipe.data.model.Nutrient
import com.recipe.data.model.Recipe
import com.recipe.data.model.RecipeSearch
import com.recipe.data.model.RecipeWithDetails
import com.recipe.network.modelDto.RecipeSearchedDTO
import com.recipe.network.modelDto.SimilarRecipe

fun EquipmentEntity.toDomain() = Equipment(
    name = name,
    image = image
)

fun InstructionEntity.toDomain() = Instruction(
    stepNumber = stepNumber,
    stepText = stepText
)

fun NutrientEntity.toDomain() = Nutrient(
    name = name,
    amountWithUnit = "${amount ?: "-"} ${unit ?: ""}".trim(),
    percentOfDailyNeeds = percentOfDailyNeeds ?: 0.0
)

fun IngredientEntity.toDomain() = Ingredient(
    name = name,
    original = original ?: "$amount $unit",
    image = image
)

fun RecipeEntity.toDomain() = Recipe(
    id = id,
    title = title,
    image = image,
    readyInMinutes = readyInMinutes,
    servings = servings,
    pricePerServing = pricePerServing?.toInt(),
    isFavorite = isFavorite,
    instructions = instructions?:"",
    summary = summary?:""
)
fun List<RecipeEntity>.toDomainRecipeList(): List<Recipe> =
    this.map { it.toDomain() }

fun RecipeSearchedDTO.toDomain(): RecipeSearch {
    return RecipeSearch(
        id = this.id,
        title = this.title,
        image = this.image,
        imageType = this.imageType
    )
}

fun SimilarRecipe.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        servings = this.servings,
        image = this.sourceUrl,
        readyInMinutes = this.readyInMinutes,
        isFavorite = false,
        pricePerServing = this.pricePerServing,
        instructions = this.instructions,
        summary = this.summary
    )
}

fun List<SimilarRecipe>.toDomainList(): List<Recipe> =
    this.map { it.toDomain() }

fun RecipeWithDetailsRelation.toDomain(): RecipeWithDetails {
    return RecipeWithDetails(
        recipe = this.recipe.toDomain(),
        ingredients = this.ingredients.map { it.toDomain() },
        nutrients = this.nutrients.map { it.toDomain() },
        instructions = this.instructions.map { it.toDomain() },
        equipment = this.equipment.map { it.toDomain() }
    )
}