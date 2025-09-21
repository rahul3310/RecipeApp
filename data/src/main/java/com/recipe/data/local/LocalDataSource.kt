package com.recipe.data.local

import com.recipe.data.dao.*
import com.recipe.data.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val nutrientDao: NutrientDao,
    private val instructionDao: InstructionDao,
    private val equipmentDao: EquipmentDao
) {
    fun getRecipeDetails(recipeId: Int): Flow<RecipeWithDetailsRelation?> {
        return recipeDao.getRecipeWithDetails(recipeId)
    }
    suspend fun insertRecipe(recipe: List<RecipeEntity>) =
        recipeDao.insertRecipe(recipe)

    suspend fun insertIngredients(ingredients: List<IngredientEntity>) =
        ingredientDao.insertIngredients(ingredients)

    suspend fun insertNutrients(nutrients: List<NutrientEntity>) =
        nutrientDao.insertNutrients(nutrients)

    suspend fun insertInstructions(instructions: List<InstructionEntity>) =
        instructionDao.insertInstructions(instructions)

    suspend fun insertEquipments(equipments: List<EquipmentEntity>) =
        equipmentDao.insertEquipments(equipments)

    fun getRecipeById(id: Int): Flow<RecipeEntity?> =
        recipeDao.getRecipeById(id)

    fun getAllRecipes(): Flow<List<RecipeEntity>> =
        recipeDao.getAllRecipes()

    fun getFavoriteRecipes(): Flow<List<RecipeEntity>> =
        recipeDao.getFavoriteRecipes()

    suspend fun updateFavorite(recipeId: Int, isFav: Boolean) =
        recipeDao.updateFavorite(recipeId, isFav)

    fun searchRecipes(query: String) = recipeDao.searchRecipes(query)
}
